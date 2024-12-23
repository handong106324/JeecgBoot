package org.jeecg.modules.demo.test.task;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.Map;

public class SolanaPriceFetcher {

    public static void main(String[] args) {



        CoinGeckoApiClient geckoPriceFetcher = new CoinGeckoApiClientImpl();
        Map<String, Map<String, Double>> price = geckoPriceFetcher.getPrice("zods,griffain", "usd");
        price.forEach((k, v) -> {
            System.out.println(k + " : " + v.get("usd"));
        });
    }
}