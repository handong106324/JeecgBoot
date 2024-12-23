package org.jeecg.modules.demo;

import com.alibaba.druid.util.MySqlUtils;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.jeecg.modules.demo.utils.MysqlUtils;

import java.sql.Connection;

public class QueryUtils {
    public GatePilotSymbol searchSymbol(String showPair) {
        IJeecgGatePilotService bean = SpringContextUtils.getBean(IJeecgGatePilotService.class);
        if (null == bean) {

        }
        GatePilotSymbol symbol = bean.search(showPair);
        return symbol;
    }


}
