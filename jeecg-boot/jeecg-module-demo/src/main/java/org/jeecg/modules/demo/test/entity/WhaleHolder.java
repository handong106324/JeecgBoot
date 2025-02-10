package org.jeecg.modules.demo.test.entity;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class WhaleHolder implements Serializable {
    private String id;

    private String chainindex;

    private String tokenaddress;

    private String symbol;

    private String address;

    private Double balance;

    private Double tokenprice;

    private String isrisktoken;

    public String insertSql() {
        return "insert into whale_holder (id,chainindex, tokenaddress, symbol, address, balance, tokenprice, isrisktoken) " +
                "values ('" + RandomUtil.randomString(10) + RandomUtil.randomNumbers(10) + "','" + chainindex + "', '" + tokenaddress + "', '" + symbol.replace("'", " ‘") + "', '" + address + "', " + balance +
                ", " + tokenprice + ", '" + isrisktoken + "')";
    }

    public String updateSql() {
        return "update whale_holder set balance = " + balance +
                " where chainindex = '" + chainindex + "' and tokenaddress = '" + tokenaddress + "' and address = '" + address + "'";
    }
}
