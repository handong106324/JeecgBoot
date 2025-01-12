package org.jeecg.modules.demo.twitter;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class TweetMeta implements Serializable {
    private String oldestId;
    private String nextToken;
    private String newestId;
    private Integer resultCount;

    public TweetMeta load(JSONObject metaJson) {
        if (metaJson == null) {
            return this;
        }
        this.oldestId= metaJson.getString("oldest_id");
        this.nextToken= metaJson.getString("next_token");
        this.newestId= metaJson.getString("newest_id");
        this.resultCount= metaJson.getInteger("result_count");
        return this;
    }
}
