package org.jeecg.modules.demo.test.task;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.domain.Coins.CoinList;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.List;

public class CoinGeckoTest {
    public static void main(String[] args) {
        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
        List<CoinList> coinList = client.getCoinList();
        for (CoinList coin : coinList) {
            System.out.println(coin.getId());
        }
    }
}
