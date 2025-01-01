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
import org.jeecg.modules.system.alert.AlertType;
import org.jeecg.modules.system.alert.WeiXinAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

		List<GatePilotSymbol> list = jeecgService.list();
		Map<String, GatePilotSymbol> oldMap =
				list.stream().collect(Collectors.toMap(GatePilotSymbol::getShowPair, v -> v));
		String s = HttpUtil.get("https://www.gate.io/apiw/v2/pilot/markets?exchange_type=PILOT_SOL_ALL");
		JSONObject result = JSONObject.parseObject(s);
		System.out.println(result.toJSONString());
		JSONArray jsonArray = result.getJSONArray("data");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			GatePilotSymbol symbol = jsonObject.toJavaObject(GatePilotSymbol.class);

			symbol.setVol_change(jsonObject.getString("change"));

			GatePilotSymbol oldSymbol = oldMap.get(symbol.getShowPair());
			if (oldSymbol == null)
			{
				jeecgService.save(symbol);
				WeiXinAlert.getInstance().sendMessage(symbol.getShowPair() + "热度变化：" + symbol.is_hot(), AlertType.ETH_ALERT);
			}
			else {
				oldSymbol.setVol_change(symbol.getVol_change());
				if (oldSymbol.is_hot() != symbol.is_hot()) {
					WeiXinAlert.getInstance().sendMessage(oldSymbol.getShowPair() + "热度变化：" + oldSymbol.is_hot() + "->" + symbol.is_hot(), AlertType.ETH_ALERT);
					oldSymbol.set_hot(symbol.is_hot());
				}
				oldSymbol.setLast(symbol.getLast());
				jeecgService.updateById(oldSymbol);
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
