package org.jeecg.modules.demo.test.service;

import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.entity.SummaryVO;


/**
 * @Description: jeecg 测试demo
 * @Author: jeecg-boot
 * @Date:  2018-12-29
 * @Version: V1.0
 */
public interface IJeecgSymbolSummaryService extends JeecgService<SummaryVO> {

    SummaryVO search(String griffain,String hour);
}
