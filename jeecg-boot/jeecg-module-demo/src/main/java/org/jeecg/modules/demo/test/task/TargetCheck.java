package org.jeecg.modules.demo.test.task;


import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
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
        CoinGeckoApiClient geckoPriceFetcher = new CoinGeckoApiClientImpl();
        Map<String, Map<String, Double>> price = geckoPriceFetcher.getPrice(collect, "usd");
        price.forEach((k, v) -> {
            System.out.println(k + " : " + v.get("usd"));
            BigThingsWeb3 bigThingsWeb3 = collect1.get(k);
            if (bigThingsWeb3 != null) {
                String monitorUrl = bigThingsWeb3.getMonitorUrl();
                String[] split = StringUtils.split(monitorUrl, ",");
                for (String s : split) {
                    String[] split1 = s.split("=");
                    if ("target".equals(split1[0])) {
                        if (Double.parseDouble(split1[1]) < v.get("usd") * 1.15 && Double.parseDouble(split1[1]) > v.get("usd") * 0.85) {
                            WeiXinAlert.getInstance().sendMessage("币种" + k + "价格" + v.get("usd") + "已经达到目标价位" + split1[1], AlertType.EOS_ALERT);
                        }
                    }
                }
            }
        });

    }
}
