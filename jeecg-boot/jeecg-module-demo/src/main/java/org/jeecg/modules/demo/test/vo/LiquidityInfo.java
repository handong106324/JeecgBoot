package org.jeecg.modules.demo.test.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LiquidityInfo implements Serializable {
    private Long createTimestamp;
    private String creatorAddress;
    private String dexName;
    private String liquidity;
    private String onlyProjectName;
    private String pairAddress;
    private List<PoolTokenInfo> poolTokenInfoList;
    private String projectId;
    private long ratio;
}
