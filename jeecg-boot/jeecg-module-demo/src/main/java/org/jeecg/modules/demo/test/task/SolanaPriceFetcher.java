package org.jeecg.modules.demo.test.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.domain.Coins.CoinFullData;
import com.litesoftwares.coingecko.domain.Coins.CoinHistoryById;
import com.litesoftwares.coingecko.domain.Coins.CoinList;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.List;
import java.util.Map;

public class SolanaPriceFetcher {
    public static void main(String[] args) {

//

//        Double price = jsonObject.getJSONObject("moew").getDouble("usd");
//        System.out.println("The price of moew in USD is: " + price);
//        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
//
//        List<CoinList> coinList = client.getCoinList();
//        for (CoinList list : coinList) {
//            String symbol = list.getSymbol();
//            String name = list.getName();
//            String id = list.getId();
//
//            try {
//                CoinFullData coinById = client.getCoinById(id);
//                long marketCapRank = coinById.getMarketCapRank();
//                Double usd = coinById.getMarketData().getMarketCap().get("usd");
//                if (null == usd) {
//                    continue;
//                }
//                System.out.println("Symbol: " + symbol + ", Name: " + name + ", Market Cap Rank: " + marketCapRank + ", Market Cap in USD: " + usd/1000000 +"M");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void getNewPairData() {
        String url = "https://debot.ai/api/dashboard/chain/recommend/new_pair?chain=solana&duration=1H&sort_field=creation_timestamp&sort_order=desc&filter=%7B%7D&is_pump=true&is_hide_honeypot=false";
        String response = HttpRequest.get(url).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(response);
        System.out.println(jsonObject.toString(SerializerFeature.PrettyFormat));
    }

    public void getCandlestickData(String address) {
       String url = "https://debot.ai/api/market?token=" + address +"&pair=&chain=solana&dex_name=pump&interval=60&start=&end=";
        String response = HttpRequest.get(url).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(response);
        System.out.println(jsonObject.toString(SerializerFeature.PrettyFormat));
    }
}