package org.jeecg.modules.demo.test.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.domain.Coins.CoinList;
import com.litesoftwares.coingecko.domain.Coins.CoinTickerById;
import com.litesoftwares.coingecko.domain.Search.Search;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import org.jeecg.modules.demo.test.vo.HolderToken;
import org.jeecg.modules.demo.test.vo.TokenPriceQuery;
import org.jeecg.modules.demo.utils.MysqlUtils;
import org.jeecg.modules.demo.utils.OKWeb3Utils;
import org.springframework.boot.actuate.endpoint.web.WebOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinGeckoCounter {
    public static void main(String[] args) {

        CoinGeckoCounter coinGeckoCounter = new CoinGeckoCounter();
        Map<String, TokenPriceQuery> map = coinGeckoCounter.addressQuery();
        List<HolderToken> holderTokens = coinGeckoCounter.holderTokens();
        List<TokenPriceQuery> query = new ArrayList<>();

        for (HolderToken holderToken : holderTokens) {
            String symbol = holderToken.getSymbol().toLowerCase();
            TokenPriceQuery tokenPriceQuery = map.get(symbol);
            if (null != tokenPriceQuery) {
                query.add(tokenPriceQuery);
            } else {
                System.out.println(symbol + " not found token");
            }
        }

        Map<String,Double> tokenPriceMap = new HashMap<>();
        JSONObject price = OKWeb3Utils.getPriceByAddress(query);
        JSONArray data = price.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject j = data.getJSONObject(i);
            Double price1 = j.getDouble("price");
            String tokeAddress = j.getString("tokenAddress");
            tokenPriceMap.put(tokeAddress, price1);
        }


        Double total = 0d;
        int tokenCount= 0;
        for (HolderToken holderToken : holderTokens) {
            Integer amount = holderToken.getAmount();
            Double costPrice = holderToken.getCostPrice();
            String symbol = holderToken.getSymbol().toLowerCase();
            TokenPriceQuery tokenPriceQuery = map.get(symbol);
            if (null != tokenPriceQuery) {
                Double v = tokenPriceMap.get(tokenPriceQuery.getTokenAddress());
                if (v != null) {
                    total += v * amount;
                    tokenCount ++;
                }
            }
        }
        System.out.println(tokenCount + "合计：" + total);
    }


    public List<HolderToken> holderTokens() {
        Connection connection = MysqlUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<HolderToken> holderTokens = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("select  * from holder_token");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String symbol = resultSet.getString("symbol");
                double costPrice = resultSet.getDouble("cost_price");
                int amount = resultSet.getInt("amount");
                HolderToken holderToken = new HolderToken();
                holderToken.setAmount(amount);
                holderToken.setSymbol(symbol);
                holderToken.setCostPrice(costPrice);
                holderTokens.add(holderToken);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MysqlUtils.close(resultSet, preparedStatement, connection);
        }

        return holderTokens;
    }
    public Map<String,TokenPriceQuery> addressQuery() {
        Map<String, TokenPriceQuery> map = new HashMap<>();
        Connection connection = MysqlUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<HolderToken> holderTokens = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("select  show_pair,address from gate_pilot_symbol");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String showPair = resultSet.getString("show_pair");
                String address = resultSet.getString("address");
                TokenPriceQuery query = new TokenPriceQuery();
                query.setChainIndex("501");
                query.setTokenAddress(address);
                map.put(showPair.split("_")[0].toLowerCase(), query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MysqlUtils.close(resultSet, preparedStatement, connection);
        }

        return map;
    }
}
