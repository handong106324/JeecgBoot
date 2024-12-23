package org.jeecg.modules.demo.test.task;

import com.alibaba.fastjson.JSONObject;

public class AllTokenOK {
    public static void main(String[] args) {
        String url = "https://www.okx.com/api/v5/dex/aggregator/all-tokens?chainId=501";
        String s = APISignature.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(jsonObject);
    }
}
