package org.jeecg.modules.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.SummaryVO;
import org.jeecg.modules.demo.test.service.IJeecgSymbolSummaryService;
import org.jeecg.modules.demo.test.task.CoinGeckoCounter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Online表单开发 demo 示例
 *
 * @author sunjianlei
 * @date 2021-12-16
 */
@Slf4j
@RestController("chatDemoController")
@RequestMapping("/demo/chart")
public class DemoWeb3Controller {

    /**
     * Online表单 http 增强，list增强示例
     * @param params
     * @return
     */
    @GetMapping("/topInfo")
    public Result<?> enhanceJavaListHttp() {
//    public Result<?> enhanceJavaListHttp(@RequestBody JSONObject params) {
//        log.info(" --- params：" + params.toJSONString());
        IJeecgSymbolSummaryService bean = SpringContextUtils.getBean(IJeecgSymbolSummaryService.class);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("dateTimeForHour");
        List<SummaryVO> list = bean.list(queryWrapper);

//        JSONArray dataList = params.getJSONArray("dataList");
//        List<DictModel> dict = virtualDictData();
//        for (int i = 0; i < dataList.size(); i++) {
//            JSONObject record = dataList.getJSONObject(i);
//            String province = record.getString("province");
//            if (province == null) {
//                continue;
//            }
//            String text = dict.stream()
//                    .filter(p -> province.equals(p.getValue()))
//                    .map(DictModel::getText)
//                    .findAny()
//                    .orElse(province);
//            record.put("province", text);
//        }
        Result<?> res = Result.OK(list);
        res.setCode(1);
        return res;
    }

    /**
     * 模拟字典数据
     *
     * @return
     */
    private List<DictModel> virtualDictData() {
        List<DictModel> dict = new ArrayList<>();
        dict.add(new DictModel("bj", "北京"));
        dict.add(new DictModel("sd", "山东"));
        dict.add(new DictModel("ah", "安徽"));
        return dict;
    }


    /**
     * Online表单 http 增强，add、edit增强示例
     * @return
     */
    @PostMapping("/total")
    public Result<?> enhanceJavaHttp() {
        CoinGeckoCounter coinGeckoCounter = new CoinGeckoCounter();

        JSONObject res = new JSONObject();
        res.put("code", 1);
        res.put("total", coinGeckoCounter.accountTotal());
        return Result.OK(res);
    }

}
