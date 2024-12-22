package org.jeecg.modules.demo.test.task;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.service.IJeecgDynamicDataService;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class GateIoPolitJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		IJeecgGatePilotService jeecgService = null;

		if (null == jeecgService) {
			jeecgService = SpringContextUtils.getBean(IJeecgGatePilotService.class);
		}
		String s = HttpUtil.get("https://www.gate.io/apiw/v2/pilot/markets?exchange_type=PILOT_SOL_ALL");
		JSONObject result = JSONObject.parseObject(s);
		System.out.println(result.toJSONString());
		JSONArray jsonArray = result.getJSONArray("data");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			System.out.println(jsonObject);
			GatePilotSymbol symbol = jsonObject.toJavaObject(GatePilotSymbol.class);

			symbol.setVol_change(jsonObject.getString("change"));

			QueryWrapper<GatePilotSymbol> queryWrapper = new QueryWrapper<>();
			List<GatePilotSymbol> showPair = jeecgService.list(queryWrapper.eq("show_pair", symbol.getShowPair()));
			if (showPair.isEmpty())
				jeecgService.save(symbol);
			else {
				GatePilotSymbol gatePilotSymbol = showPair.get(0);
				gatePilotSymbol.setVol_change(symbol.getVol_change());
				gatePilotSymbol.set_hot(symbol.is_hot());
				gatePilotSymbol.setLast(symbol.getLast());
				jeecgService.updateById(gatePilotSymbol);
			}
		}
	}

	public static void main(String[] args) {
		GateIoPolitJob job = new GateIoPolitJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
