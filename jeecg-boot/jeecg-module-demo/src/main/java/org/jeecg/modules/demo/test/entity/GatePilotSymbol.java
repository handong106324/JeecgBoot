package org.jeecg.modules.demo.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="gate创新区", description="测试DEMO")
@TableName("gate_pilot_symbol")
public class GatePilotSymbol extends JeecgEntity implements Serializable {
    private String cate;
    private String chain;
    private String address;

    private String last;

    private String vol_change;
    private String icon;

    private String quotePrec;

    private String pair;

    private String seoPath;

    private int amountPrec;

    private boolean is_hot;

    private String showPair;

    private String vol_24h;

    private String name;

}
