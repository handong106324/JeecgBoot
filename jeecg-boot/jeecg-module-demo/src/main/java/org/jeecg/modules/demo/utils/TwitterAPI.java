package org.jeecg.modules.demo.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TwitterAPI {

    public static void getFollowers(String bearerToken, String userId) {
        try {
            URL url = new URL("https://api.twitter.com/2/users/" + userId + "/followers");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 打印或处理响应
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}