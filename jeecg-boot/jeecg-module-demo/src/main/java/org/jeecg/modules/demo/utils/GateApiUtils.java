package org.jeecg.modules.demo.utils;

import io.gate.gateapi.ApiClient;
import io.gate.gateapi.ApiException;
import io.gate.gateapi.api.*;
import io.gate.gateapi.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GateApiUtils {

    private static class SingletonHolder {
        private static final GateApiUtils INSTANCE = new GateApiUtils();
    }
    public static GateApiUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiClient client;
    public SpotApi spotApi;
    public List<String> spotSymbols = new ArrayList<>();
    private Map<String,Double> priceMap = new HashMap<>();
    private GateApiUtils() {
        client = new ApiClient();
        client.setApiKeySecret("4717da20946b7d7102a65563fb55d221", "733be1e46138cbe6f8c4ed07f6ff3bf773ce4ab7481671edf8a7b5c17cd7b491");
        spotApi = new SpotApi(client);
        List<CurrencyPair> currencies = null;
        try {
            currencies = spotApi.listCurrencyPairs();
            for (CurrencyPair currency : currencies) {
                spotSymbols.add(currency.getBase());
            }
        } catch (Exception e) {

        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        tickerSpot();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(10 * 60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public Map<String,Double> tickerSpot() {


        SpotApi.APIlistTickersRequest apIlistTickersRequest = spotApi.listTickers();
        List<Ticker> execute = null;
        try {
            execute = apIlistTickersRequest.execute();
            for (Ticker ticker : execute) {
                priceMap.put(ticker.getCurrencyPair().toLowerCase(), Double.parseDouble(ticker.getLast()));
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return priceMap;
    }

}
