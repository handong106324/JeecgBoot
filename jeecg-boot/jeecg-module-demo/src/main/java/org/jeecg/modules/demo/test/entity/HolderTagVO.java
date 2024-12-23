package org.jeecg.modules.demo.test.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class HolderTagVO implements Serializable {
    private String creator;
    private String liquidityPool;

    private String dev;

    private String snipers;

    private String smartMoney;

    private String suspicious;
}
