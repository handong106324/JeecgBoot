package org.jeecg.modules.demo.test.task;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;

public class APISignature {

    public static void main(String[] args) {
        try {
            // 示例参数

        } catch (Exception e) {
            e.printStackTrace();
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