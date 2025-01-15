package org.jeecg.modules.demo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.demo.test.task.APISignature;
import org.jeecg.modules.demo.test.vo.HolderToken;
import org.jeecg.modules.demo.test.vo.TokenPriceQuery;

import java.util.List;

public class OKWeb3Utils {
    public static JSONObject getTransactionByAddress(String address, String chains, String begin) {
        String url = "/api/v5/wallet/post-transaction/transactions-by-address"+"?address=" +
                address +"&chains=" + chains+ "&filter=1&begin=" + begin;
        String s = APISignature.doGet(url);
        return JSONObject.parseObject(s);
    }
    public static JSONObject getBalancesByAddress(String address, String chains) {
        String url = "/api/v5/wallet/asset/all-token-balances-by-address"+"?address=" + address +"&chains=" + chains+ "&filter=1";
        String s = APISignature.doGet(url);
        return JSONObject.parseObject(s);
    }
    public static JSONObject getPriceByAddress(List<TokenPriceQuery> tokenList) {
        String url = "/api/v5/wallet/token/real-time-price";//+"?tokenAddress=" + address +"&chainIndex=" + chains+ "&filter=1";
        String s = APISignature.doPost(url, JSONArray.toJSONString(tokenList));
        return JSONObject.parseObject(s);
    }
}
