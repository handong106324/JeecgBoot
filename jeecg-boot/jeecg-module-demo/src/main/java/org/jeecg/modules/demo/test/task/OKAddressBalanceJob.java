package org.jeecg.modules.demo.test.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.jeecg.modules.demo.utils.OKWeb3Utils;
import org.jeecg.modules.message.util.PushMsgUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class OKAddressBalanceJob implements Job {

	PushMsgUtil pushMsgUtil;

	private static String BASE_INDEX = "8453";
	private static String SOL_INDEX = "501";
	/**
	 * 若参数变量名修改 QuartzJobController中也需对应修改
	 */
	private String parameter;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		pushMsgUtil = SpringContextUtils.getBean(PushMsgUtil.class);
		IJeecgGatePilotService bean = SpringContextUtils.getBean(IJeecgGatePilotService.class);
		String[] split = StringUtils.split(parameter, ",");
		for (String s : split) {
			GatePilotSymbol search = bean.search(s);
			if (null != search) {
				try {
					walletParse(search);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				log.error(parameter + " not found");
			}
		}

	}



	public void walletParse(GatePilotSymbol symbol) throws Exception {

		System.out.println(OKWeb3Utils.getBalancesByAddress(symbol.getAddress(), "501"));

	}
	public static void main(String[] args) {
		GatePilotSymbol gatePilotSymbol = new GatePilotSymbol();
		gatePilotSymbol.setShowPair("GRIFFAIN_USDT");
		gatePilotSymbol.setAddress("5Q544fKrFoe6tsEbD7S8EmxGTJYAKtTVhAW5Q5pge4j1");
//		gatePilotSymbol.setAddress("0x9642b23ed1e01df1092b92641051881a322f5d4e");
		OKAddressBalanceJob okHolderJob = new OKAddressBalanceJob();
        try {
            okHolderJob.walletParse(gatePilotSymbol);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
