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
@ApiModel(value="币种前排监控", description="大鲸鱼持仓监控")
@TableName("web_top_info")
public class SummaryVO  extends JeecgEntity implements Serializable {

    private String symbol;

    private String suspiciousTagHolderAmount;

    private String top10HoldAmountPercentage;

    private String top30HoldAmountPercentage;

    private String top50HoldAmountPercentage;

    private String sniperTagHolderAmount;

    private String totalHolderAmount;

    private String dateTimeForHour;
}
