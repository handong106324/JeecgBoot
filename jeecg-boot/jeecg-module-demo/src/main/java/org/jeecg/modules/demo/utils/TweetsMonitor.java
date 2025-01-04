package org.jeecg.modules.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TweetsMonitor {
    public static void main(String[] args) {
        try {
            Thread.sleep(10 * 60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                new TweetsMonitor().queryTweets();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void queryTweets() {
        try {
            List<String> strings = FileUtils.readLines(new File("./ccc.txt"));
            List<String> ids = new ArrayList<>();
            for (String string : strings) {
                String string1 = JSONObject.parseObject(string).getJSONObject("data").getString("id");
                ids.add(string1);
                System.out.println(string1);
            }
            String tweets = TwitterUtils.getTweets(ids.stream().collect(Collectors.joining(",")));
            FileUtils.writeLines(new File("./tweets.txt"), Arrays.asList(tweets), true);
            FileUtils.writeLines(new File("./tweets.txt"), Arrays.asList("\n"), true);
            for (String id : ids) {
                String tweetsByUserId = TwitterUtils.getTweetsByUserId(id);
                FileUtils.writeLines(new File("./tweets.txt"), Arrays.asList(tweetsByUserId), true);
                Thread.sleep(16 * 60000);
            }
            FileUtils.writeLines(new File("./tweets.txt"), Arrays.asList("\n"), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
