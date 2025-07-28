import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.WhaleAddressMonitor;
import org.jeecg.modules.demo.test.entity.WhaleHolder;
import org.jeecg.modules.demo.test.entity.WhaleTranctionsHistory;
import org.jeecg.modules.demo.test.service.IWhaleAddressMonitorService;
import org.jeecg.modules.demo.test.service.IWhaleTranctionsHistoryService;
import org.jeecg.modules.demo.test.task.OKAddressBalanceJob;
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
        OKAddressBalanceJob okAddressBalanceJob = new OKAddressBalanceJob();
        okAddressBalanceJob.text();
    }

}
