package org.jeecg.modules.demo.twitter;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class TweetData implements Serializable {
    private String createdAt;

    private String text;

    private String id;

    public TweetData load(JSONObject dataJson) {

        id = dataJson.getString("id");
        text = dataJson.getString("text");
        createdAt = dataJson.getString("created_at");
        return this;
    }
}
