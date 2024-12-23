package org.jeecg.modules.demo.test.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class HolderRank implements Serializable {
    private String holdAmount4HChange;

    private String chainId;

    private String holdVolume;

    private String explorerUrl;

    private String holderWalletAddress;

    private String holdAmount;

    private String holdAmount1HChange;

    private String holdAmount24HChange;

    private String holdAmountPercentage;
}
