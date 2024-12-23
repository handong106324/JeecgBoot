package org.jeecg.modules.demo.test.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.BigThingsWeb3;
import org.jeecg.modules.demo.test.service.IBigThingsWeb3Service;
import org.jeecg.modules.system.alert.AlertType;
import org.jeecg.modules.system.alert.WeiXinAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TargetCheck implements Job {
    public static void main(String[] args) {

    }

    private IBigThingsWeb3Service iBigThingsWeb3Service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        iBigThingsWeb3Service = SpringContextUtils.getBean(IBigThingsWeb3Service.class);

        List<BigThingsWeb3> list = iBigThingsWeb3Service.list();
        Map<String, BigThingsWeb3> collect1 = list.stream().collect(Collectors.toMap(BigThingsWeb3::getSymbol, v -> v));
        String collect = list.stream().map(BigThingsWeb3::getSymbol).collect(Collectors.joining(","));

        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + collect+ "&vs_currencies=usd";
        String response = HttpRequest.get(url).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(response);

        for (String key : jsonObject.keySet()) {
            Double usd = jsonObject.getJSONObject(key).getDouble("usd");
            BigThingsWeb3 bigThingsWeb3 = collect1.get(key);
            if (bigThingsWeb3 != null) {
                String monitorUrl = bigThingsWeb3.getMonitorUrl();
                String[] split = StringUtils.split(monitorUrl, ",");
                for (String s : split) {
                    String[] split1 = s.split("=");
                    if ("target".equals(split1[0])) {
                        if (Double.parseDouble(split1[1]) <usd * 1.15 && Double.parseDouble(split1[1]) > usd * 0.85) {
                            WeiXinAlert.getInstance().sendMessage("币种" + s + "价格" + usd + "已经达到目标价位" + split1[1], AlertType.EOS_ALERT);
                        }
                    }
                }
            }
        }

    }
}
