package org.jeecg.modules.demo.test.task;

import cn.hutool.http.HttpRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Base64;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.TimeZone;

public class APISignature {

    private static final String OKX= "https://www.okx.com";
    public static String doGet(String url) {
        String apiKey = "3e13599d-959d-4caa-a800-ab57d96617b2";
        String secretKey = "935A932780D9AFE7BEA6A779390A0249";
        String passphrase = "Xinran0624f6zx@4qc";
        String projectId = "65cdadb511b04810d72227dff0cd0637";
        String method = "GET";
        String body = ""; // GET 请求没有 body

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");;
        timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp =timeFormatter.format(new Date());
        System.out.println(timestamp);
        // 生成签名
        try {
            String sign = generateSignature(timestamp, method, url, body, secretKey);
            String s = HttpRequest.get(OKX + url)
                    .header("OK-ACCESS-KEY", apiKey)
                    .header("OK-ACCESS-SIGN",sign)
                    .header("Content-Type","application/json")
                    .header("OK-ACCESS-TOKEN",sign)
                    .header("OK-ACCESS-PROJECT",projectId)
                    .header("OK-ACCESS-PASSPHRASE",passphrase)
                    .header("OK-ACCESS-TIMESTAMP", timestamp)
                    .execute().body();
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String doPost(String url, String body) {
        String apiKey = "3e13599d-959d-4caa-a800-ab57d96617b2";
        String secretKey = "935A932780D9AFE7BEA6A779390A0249";
        String passphrase = "Xinran0624f6zx@4qc";
        String projectId = "65cdadb511b04810d72227dff0cd0637";
        String method = "POST";

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");;
        timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp =timeFormatter.format(new Date());
        System.out.println(timestamp);
        // 生成签名
        try {
            String sign = generateSignature(timestamp, method, url, body, secretKey);
            String s = HttpRequest.post(OKX + url).body(body)
                    .header("OK-ACCESS-KEY", apiKey)
                    .header("OK-ACCESS-SIGN",sign)
                    .header("Content-Type","application/json")
                    .header("OK-ACCESS-TOKEN",sign)
                    .header("OK-ACCESS-PROJECT",projectId)
                    .header("OK-ACCESS-PASSPHRASE",passphrase)
                    .header("OK-ACCESS-TIMESTAMP", timestamp)
                    .execute().body();
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSignature(String timestamp, String method, String requestPath, String body, String secretKey) throws Exception {
        // 连接要签名的字符串
        String preHash = timestamp + method.toUpperCase() + requestPath + body;

        // 创建 HMAC SHA256 实例
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // 初始化 MAC 实例
        sha256_HMAC.init(secret_key);

        // 生成 HMAC SHA256 哈希
        byte[] hashBytes = sha256_HMAC.doFinal(preHash.getBytes(StandardCharsets.UTF_8));

        // 将哈希值编码为 Base-64
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}