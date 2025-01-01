package org.jeecg.modules.demo.test.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.WhaleAddressMonitor;
import org.jeecg.modules.demo.test.entity.WhaleTranctionsHistory;
import org.jeecg.modules.demo.test.service.IWhaleAddressMonitorService;
import org.jeecg.modules.demo.test.service.IWhaleTranctionsHistoryService;
import org.jeecg.modules.demo.utils.OKWeb3Utils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class OKAddressTransactionJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		IWhaleAddressMonitorService bean = SpringContextUtils.getBean(IWhaleAddressMonitorService.class);
		for (WhaleAddressMonitor whaleAddressMonitor : bean.list()) {
			try {
				walletParse(whaleAddressMonitor);
			} catch (Exception e) {
				log.error("error", e);
			}
		}

	}

	public void walletParse(WhaleAddressMonitor gatePilotSymbol) throws Exception {

		IWhaleTranctionsHistoryService whaleTranctionsHistoryService = SpringContextUtils.getBean(IWhaleTranctionsHistoryService.class);
		if (whaleTranctionsHistoryService == null) {
			System.out.println("whaleTranctionsHistoryService is null");
			return;
		}
		QueryWrapper<WhaleTranctionsHistory> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("txtime").last("limit 1");
		List<WhaleTranctionsHistory> list = whaleTranctionsHistoryService.list(queryWrapper);
		String begin = "";
		if (!list.isEmpty()) {
			begin = list.get(0).getTxtime().getTime() + "";
		}

		JSONObject transactionByAddress = OKWeb3Utils.getTransactionByAddress(gatePilotSymbol.getAddress(), gatePilotSymbol.getChainId(), begin);

		List<WhaleTranctionsHistory> historyList = new ArrayList<>();
		if (transactionByAddress != null && transactionByAddress.getString("code").equals("0")) {
			System.out.println("success GET");
			JSONArray data = transactionByAddress.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject da = data.getJSONObject(i);
				String cursor = da.getString("cursor");
				JSONArray transactionList = da.getJSONArray("transactionList");
				for (int i1 = 0; i1 < transactionList.size(); i1++) {
					JSONObject jsonObject = transactionList.getJSONObject(i1);
					String symbol = jsonObject.getString("symbol");
					Double amount = jsonObject.getDouble("amount");
					Long txTime = jsonObject.getLong("txTime");
					String chainIndex = jsonObject.getString("chainIndex");
					String tokenAddress = jsonObject.getString("tokenAddress");
					String txStatus = jsonObject.getString("txStatus");
					String txHash = jsonObject.getString("txHash");
					String from = jsonObject.getString("from");
					WhaleTranctionsHistory whaleTranctionsHistory = new WhaleTranctionsHistory();
					whaleTranctionsHistory.setSymbol(symbol);
					whaleTranctionsHistory.setAmount(amount);
					whaleTranctionsHistory.setTxtime(new Date(txTime));
					whaleTranctionsHistory.setChainindex(chainIndex);
					whaleTranctionsHistory.setTokenaddress(tokenAddress);
					whaleTranctionsHistory.setTxstatus(txStatus);
					whaleTranctionsHistory.setTxhash(txHash);
					whaleTranctionsHistory.setFromaddress(from);
					whaleTranctionsHistory.setQueryCursor(cursor);
					historyList.add(whaleTranctionsHistory);
				}

			}
			if (!historyList.isEmpty()) {
				whaleTranctionsHistoryService.saveBatch(historyList);
			}
		} else {
			System.out.println("fail:" + transactionByAddress.getString("msg"));
		}

	}

}
