package org.jeecg.modules.demo.test.task;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.entity.HolderRank;
import org.jeecg.modules.demo.test.entity.SummaryVO;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.jeecg.modules.demo.test.service.IJeecgSymbolSummaryService;
import org.jeecg.modules.message.util.PushMsgUtil;
import org.jeecg.modules.system.alert.AlertType;
import org.jeecg.modules.system.alert.WeiXinAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class OkHolderJob implements Job {

	PushMsgUtil pushMsgUtil;
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

	private static String HOLDER_HEADER = "https://www.okx.com/priapi/v1/dx/market/v2/holders/ranking-list?chainId=501&tokenAddress=";


	public void walletParse(GatePilotSymbol symbol) throws Exception {
		String apiKey = "3e13599d-959d-4caa-a800-ab57d96617b2";
		String secretKey = "935A932780D9AFE7BEA6A779390A0249";
		String passphrase = "Xinran0624f6zx@4qc";
		String method = "GET";
		String body = ""; // GET 请求没有 body
		String timestamp = Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

		// 生成签名
		String sign = APISignature.generateSignature(timestamp, method, HOLDER_HEADER + symbol.getAddress(), body, secretKey);


		String s = HttpRequest.get(HOLDER_HEADER + symbol.getAddress())
				.header("OK-ACCESS-KEY", apiKey)
				.header("OK-ACCESS-TOKEN",sign)
				.header("OK-ACCESS-SIGN",sign)
				.header("OK-ACCESS-PASSPHRASE",passphrase)
				.header("OK-ACCESS-TIMESTAMP", timestamp)
				.execute().body();
		JSONObject result = JSONObject.parseObject(s);
		System.out.println(result.toString(SerializerFeature.PrettyFormat));
		SummaryVO summaryVO = result.getJSONObject("data").getJSONObject("summaryVO").toJavaObject(SummaryVO.class);
		List<HolderRank> holderRankingList = result.getJSONObject("data").getJSONArray("holderRankingList").toJavaList(HolderRank.class);
		summaryVO.setSymbol(symbol.getShowPair());
		double amount30 = 0;
		double amount50 = 0;
		for (int i = 0; i < holderRankingList.size(); i++) {
			amount30 += Double.parseDouble(holderRankingList.get(i).getHoldAmountPercentage());
			amount50 += Double.parseDouble(holderRankingList.get(i).getHoldAmountPercentage());
			if (i == 29) {
				summaryVO.setTop30HoldAmountPercentage(amount30 + "");
			}

			if (i == 49) {
				summaryVO.setTop50HoldAmountPercentage(amount50 + "");
				break;
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		summaryVO.setDateTimeForHour(simpleDateFormat.format(new Date()));

		WeiXinAlert.getInstance().sendMessage(JSONObject.toJSONString(summaryVO, SerializerFeature.PrettyFormat), AlertType.BTC_ALERT);

		IJeecgSymbolSummaryService bean = SpringContextUtils.getBean(IJeecgSymbolSummaryService.class);
		if (null != bean) {
			SummaryVO search = bean.search(symbol.getShowPair(), summaryVO.getDateTimeForHour());
			if (null == search) {
				bean.save(summaryVO);
			} else {
				BeanUtil.copyProperties(summaryVO, search);
				bean.updateById(search);
			}
		}
		//入库

	}

	public static void main(String[] args) {

		System.out.println(APISignature.doGet(HOLDER_HEADER + "KENJSUYLASHUMfHyy5o4Hp2FdNqZg1AsUPhfH2kYvEP"));
//		GatePilotSymbol gatePilotSymbol = new GatePilotSymbol();
//		gatePilotSymbol.setShowPair("GRIFFAIN_USDT");
//		gatePilotSymbol.setAddress("KENJSUYLASHUMfHyy5o4Hp2FdNqZg1AsUPhfH2kYvEP");
//		OkHolderJob okHolderJob = new OkHolderJob();
//        try {
//            okHolderJob.walletParse(gatePilotSymbol);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
