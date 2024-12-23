package org.jeecg.modules.demo.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="币种前排监控", description="大鲸鱼持仓监控")
@TableName("web_top_info")
public class SummaryVO  extends JeecgEntity implements Serializable {

    private String symbol;

    @TableField("suspiciousTagHolderAmount")
    private String suspiciousTagHolderAmount;

    @TableField("top10HoldAmountPercentage")
    private String top10HoldAmountPercentage;

    @TableField("top30HoldAmountPercentage")
    private String top30HoldAmountPercentage;

    @TableField("top50HoldAmountPercentage")
    private String top50HoldAmountPercentage;

    @TableField("sniperTagHolderAmount")
    private String sniperTagHolderAmount;

    @TableField("totalHolderAmount")
    private String totalHolderAmount;

    @TableField("dateTimeForHour")
    private String dateTimeForHour;
}
