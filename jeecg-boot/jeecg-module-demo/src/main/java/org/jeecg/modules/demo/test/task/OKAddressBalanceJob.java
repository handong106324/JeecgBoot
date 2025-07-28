package org.jeecg.modules.demo.test.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.entity.WhaleAddressMonitor;
import org.jeecg.modules.demo.test.entity.WhaleHolder;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.jeecg.modules.demo.utils.MysqlUtils;
import org.jeecg.modules.demo.utils.OKWeb3Utils;
import org.jeecg.modules.message.util.PushMsgUtil;
import org.jeecg.modules.system.alert.AlertType;
import org.jeecg.modules.system.alert.WeiXinAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class OKAddressBalanceJob implements Job {

	/**
	 * 若参数变量名修改 QuartzJobController中也需对应修改
	 */
	private String parameter;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		text();
	}

	public void text() {
		List<WhaleAddressMonitor> whaleAddressMonitors = MysqlUtils.queryList("select * from whale_address_monitor", WhaleAddressMonitor.class);
		for (WhaleAddressMonitor whaleAddressMonitor : whaleAddressMonitors) {
			try {
				walletParse(whaleAddressMonitor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void walletParse(WhaleAddressMonitor gatePilotSymbol) throws Exception {

		JSONObject transactionByAddress = OKWeb3Utils.getBalancesByAddress(gatePilotSymbol.getAddress(), gatePilotSymbol.getChainId());

		List<WhaleHolder> whaleHolders = MysqlUtils.queryList("select * from whale_holder where address = '" + gatePilotSymbol.getAddress() + "'", WhaleHolder.class);
		Map<String, WhaleHolder> collect = whaleHolders.stream().collect(Collectors.toMap(WhaleHolder::getTokenaddress, v -> v));
		List<String> sqls = new ArrayList<>();
		String operations = gatePilotSymbol.getName() + " 新增操作: \n";
		if (transactionByAddress != null && transactionByAddress.getString("code").equals("0")) {
			JSONArray data = transactionByAddress.getJSONArray("data");

			for (int i = 0; i < data.size(); i++) {
				JSONArray tokenAddress = data.getJSONObject(i).getJSONArray("tokenAssets");
				for (int i1 = 0; i1 < tokenAddress.size(); i1++) {
					WhaleHolder whaleHolder = tokenAddress.getObject(i1, WhaleHolder.class);
					WhaleHolder whaleHolder1 = collect.get(whaleHolder.getTokenaddress());
					if (whaleHolder1 != null) {
						if (whaleHolder.getBalance().intValue() == whaleHolder1.getBalance().intValue()) {
							continue;
						}
						if (Math.abs(whaleHolder.getBalance().intValue() - whaleHolder1.getBalance().intValue())/whaleHolder.getBalance() <= 0.05) {
							continue;
						}
						operations += "    " + whaleHolder.getSymbol() + " update:" + (whaleHolder1.getBalance().intValue() - whaleHolder.getBalance().intValue())
								+ "at " + whaleHolder.getTokenprice().toString() + " then income "
						+ new BigDecimal(whaleHolder1.getBalance() * whaleHolder1.getTokenprice() - whaleHolder.getBalance() + whaleHolder.getTokenprice()).intValue() + "$\n";
						sqls.add(whaleHolder.updateSql());
					} else {
						operations += "    " + whaleHolder.getSymbol() + " add:" + (whaleHolder.getBalance().intValue()) + " at " +whaleHolder.getTokenprice().toString() + "\n";
						sqls.add(whaleHolder.insertSql());
					}
				}

			}

			if (sqls.size() > 0) {
				WeiXinAlert.getInstance().sendMessage(operations, AlertType.EOS_ALERT);
				MysqlUtils.batchUpdate(sqls);
			}

		} else {
			System.out.println("fail:" + transactionByAddress.getString("msg"));
		}

	}

}
