package org.jeecg.modules.demo.test.task;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class GateIoHolderJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

//		String s = HttpUtil.get("https://www.okx.com/priapi/v1/dx/market/v2/token/overview?chainId=501&tokenContractAddress=KENJSUYLASHUMfHyy5o4Hp2FdNqZg1AsUPhfH2kYvEP");
//		JSONObject result = JSONObject.parseObject(s);
//		System.out.println(result.toJSONString());
//		JSONArray jsonArray = result.getJSONArray("data");
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONObject jsonObject = jsonArray.getJSONObject(i);
//			System.out.println(jsonObject);
//			GatePilotSymbol symbol = jsonObject.toJavaObject(GatePilotSymbol.class);
//
//			symbol.setVol_change(jsonObject.getString("change"));
//
//			QueryWrapper<GatePilotSymbol> queryWrapper = new QueryWrapper<>();
//
//		}
	}

	private static String HOLDER_HEADER = "https://www.okx.com/priapi/v1/dx/market/v2/holders/ranking-list?chainId=501&tokenAddress=KENJSUYLASHUMfHyy5o4Hp2FdNqZg1AsUPhfH2kYvEP";
	private static String WALLET_ADDRESS_PARSE = "https://www.okx.com/priapi/v1/dx/market/v2/holders/ranking-list?chainId=501&tokenAddress=KENJSUYLASHUMfHyy5o4Hp2FdNqZg1AsUPhfH2kYvEP";

	public static void holderParse() {
		String s = HttpUtil.get(HOLDER_HEADER);
		JSONObject result = JSONObject.parseObject(s);
		System.out.println(result.toString(SerializerFeature.PrettyFormat));
	}
	public static String hmacSha256Base64(String secret, String message) throws Exception {
		// 创建 HMAC SHA256 实例
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");

		// 初始化 MAC 实例
		sha256_HMAC.init(secret_key);

		// 生成 HMAC SHA256 哈希
		byte[] hashBytes = sha256_HMAC.doFinal(message.getBytes("UTF-8"));

		// 将哈希值编码为 Base-64
		return Base64.getEncoder().encodeToString(hashBytes);
	}
	public static void walletParse() throws Exception {
		String apiKey = "3e13599d-959d-4caa-a800-ab57d96617b2";
		String secretKey = "935A932780D9AFE7BEA6A779390A0249";
		String passphrase = "Xinran0624f6zx@4qc";
		String method = "GET";
		String body = ""; // GET 请求没有 body
		String timestamp = Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

		// 生成签名
		String sign = APISignature.generateSignature(timestamp, method, WALLET_ADDRESS_PARSE, body, secretKey);

		// 打印请求头
		System.out.println("OK-ACCESS-KEY: " + apiKey);
		System.out.println("OK-ACCESS-SIGN: " + sign);
		System.out.println("OK-ACCESS-TIMESTAMP: " + timestamp);
		System.out.println("OK-ACCESS-PASSPHRASE: " + passphrase);
		String s = HttpRequest.get(HOLDER_HEADER)
				.header("OK-ACCESS-KEY", apiKey)
				.header("OK-ACCESS-TOKEN",sign)
				.header("OK-ACCESS-PASSPHRASE",passphrase)
				.header("OK-ACCESS-TIMESTAMP", timestamp)
				.execute().body();
		JSONObject result = JSONObject.parseObject(s);
		System.out.println(result.toString(SerializerFeature.PrettyFormat));
	}

	public static void main(String[] args) {
		GateIoHolderJob job = new GateIoHolderJob();
		try {
			GateIoHolderJob.walletParse();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
