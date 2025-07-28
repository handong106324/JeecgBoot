package org.jeecg.modules.demo;

import com.alibaba.druid.util.MySqlUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.BigThingsWeb3;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.jeecg.modules.demo.utils.MysqlUtils;

import java.sql.Connection;

public class QueryUtils {
    public static void main(String[] args) {
        BigThingsWeb3 gatePilotSymbol = null;

        JSONObject jsonObject = JSONObject.parseObject("{'update_by':'handong','update_time':'2021-07-07 16:00:00','create_by':'handong','create_time':'2021-07-07 16:00:00'}");
        BigThingsWeb3 javaObject = jsonObject.toJavaObject(BigThingsWeb3.class);
        System.out.println(javaObject.getCreateBy());
    }

}
