package org.jeecg.modules.demo.twitter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TweetsData implements Serializable {
    private List<TweetData> dataList = new ArrayList<>();

    private TweetMeta meta;

    public TweetsData load(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            dataList.add(new TweetData().load(object));
        }
        return this;
    }
}
