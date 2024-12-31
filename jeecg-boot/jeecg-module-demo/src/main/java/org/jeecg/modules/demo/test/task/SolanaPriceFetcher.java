package org.jeecg.modules.demo.test.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.Map;

public class SolanaPriceFetcher {
    public static void main(String[] args) {

        String url = "https://api.coingecko.com/api/v3/simple/price?ids=moew&vs_currencies=usd";
        String response = HttpRequest.get(url).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(response);
        Double price = jsonObject.getJSONObject("moew").getDouble("usd");
        System.out.println("The price of moew in USD is: " + price);
    }
}