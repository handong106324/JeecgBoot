package org.jeecg.modules.demo.test.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class PoolTokenInfo implements Serializable {
    private Double amount;
    private String tokenContractAddress;
    private String tokenSymbol;
    private Double tokenAmountUsd;


}
