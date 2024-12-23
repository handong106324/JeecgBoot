package org.jeecg.modules.demo.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.test.entity.GatePilotSymbol;
import org.jeecg.modules.demo.test.entity.SummaryVO;
import org.jeecg.modules.demo.test.mapper.GatePilotMapper;
import org.jeecg.modules.demo.test.mapper.SummaryMapper;
import org.jeecg.modules.demo.test.service.IJeecgGatePilotService;
import org.jeecg.modules.demo.test.service.IJeecgSymbolSummaryService;
import org.springframework.stereotype.Service;

/**
 * @Description: jeecg 测试demo
 * @Author: jeecg-boot
 * @Date:  2018-12-29
 * @Version: V1.0
 */
@Service
public class SummaryServiceImpl extends ServiceImpl<SummaryMapper, SummaryVO> implements IJeecgSymbolSummaryService {

    @Override
    public SummaryVO search(String griffain,String dateTime) {
        QueryWrapper<SummaryVO> gatePilotSymbolQueryWrapper = new QueryWrapper<>();
        gatePilotSymbolQueryWrapper.eq("show_pair", griffain).eq("hour", dateTime);
        return getOne(gatePilotSymbolQueryWrapper);
    }
}
