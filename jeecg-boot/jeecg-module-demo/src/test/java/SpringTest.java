import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.WhaleAddressMonitor;
import org.jeecg.modules.demo.test.entity.WhaleHolder;
import org.jeecg.modules.demo.test.entity.WhaleTranctionsHistory;
import org.jeecg.modules.demo.test.service.IWhaleAddressMonitorService;
import org.jeecg.modules.demo.test.service.IWhaleTranctionsHistoryService;
import org.jeecg.modules.demo.utils.MysqlUtils;
import org.jeecg.modules.demo.utils.OKWeb3Utils;
import org.jeecg.modules.system.alert.AlertType;
import org.jeecg.modules.system.alert.WeiXinAlert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class SpringTest {

    @Test
    public void text() {
        List<WhaleAddressMonitor> whaleAddressMonitors = MysqlUtils.queryList("select * from whale_address_monitor", WhaleAddressMonitor.class);
        for (WhaleAddressMonitor whaleAddressMonitor : whaleAddressMonitors) {
            try {
                walletParse(whaleAddressMonitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void walletParse(WhaleAddressMonitor gatePilotSymbol) throws Exception {

        JSONObject transactionByAddress = OKWeb3Utils.getBalancesByAddress(gatePilotSymbol.getAddress(), gatePilotSymbol.getChainId());

        List<WhaleHolder> whaleHolders = MysqlUtils.queryList("select * from whale_holder where address = '" + gatePilotSymbol.getAddress() + "'", WhaleHolder.class);
        Map<String, WhaleHolder> collect = whaleHolders.stream().collect(Collectors.toMap(WhaleHolder::getTokenaddress, v -> v));
        List<String> sqls = new ArrayList<>();
        String operations = gatePilotSymbol.getName() + " 新增操作: \n";
        if (transactionByAddress != null && transactionByAddress.getString("code").equals("0")) {
            JSONArray data = transactionByAddress.getJSONArray("data");

            for (int i = 0; i < data.size(); i++) {
                JSONArray tokenAddress = data.getJSONObject(i).getJSONArray("tokenAssets");
                for (int i1 = 0; i1 < tokenAddress.size(); i1++) {
                    WhaleHolder whaleHolder = tokenAddress.getObject(i1, WhaleHolder.class);
                    WhaleHolder whaleHolder1 = collect.get(whaleHolder.getTokenaddress());
                    if (whaleHolder1 != null) {
                        if (whaleHolder.getBalance().intValue() == whaleHolder1.getBalance().intValue()) {
                            continue;
                        }
                        if (Math.abs(whaleHolder.getBalance().intValue() - whaleHolder1.getBalance().intValue())/whaleHolder.getBalance() <= 0.05) {
                            continue;
                        }
                        System.out.println(whaleHolder.getSymbol() + " update:" + (whaleHolder1.getBalance() - whaleHolder.getBalance()));
                        operations += "    " + whaleHolder.getSymbol() + " update:" + (whaleHolder1.getBalance() - whaleHolder.getBalance()) + "\n";
                        sqls.add(whaleHolder.updateSql());
                    } else {
                        System.out.println(whaleHolder.getSymbol() + " add " + whaleHolder.getBalance());
                        operations += "    " + whaleHolder.getSymbol() + " add:" + (whaleHolder.getBalance()) + "\n";
                        sqls.add(whaleHolder.insertSql());
                    }
                }

            }

            if (sqls.size() > 0) {
                WeiXinAlert.getInstance().sendMessage(operations, AlertType.EOS_ALERT);
                MysqlUtils.batchUpdate(sqls);
            }

        } else {
            System.out.println("fail:" + transactionByAddress.getString("msg"));
        }

    }


}
