package com.xust.wtc.Service.logistics.impl;

import com.xust.wtc.Dao.logistics.LogisticsMapper;
import com.xust.wtc.Entity.Company;
import com.xust.wtc.Entity.DisplayLogistics;
import com.xust.wtc.Entity.Logistics;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.logistics.LogisticsService;
import com.xust.wtc.utils.KDQuery;
import com.xust.wtc.utils.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Spirit on 2018/1/9.
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsMapper logisticsMapper;

    /**
     * 增加一个物流信息
     * @param lendId
     * @return
     */
    @Override
    public int addLogistics(int lendId) {
        return logisticsMapper.addLogistics(lendId);
    }

    /**
     * 更新物流信息
     * @param logistics
     * @return
     */
    @Override
    public Result updateLogistics(Logistics logistics) {
        Result result = new Result();
        if (logisticsMapper.updateLogistics(logistics) > 0) {
            result.setStatus(1);
            result.setContent("修改物流信息成功");
        } else {
            result.setStatus(0);
            result.setContent("修改物流信息失败");
        }
        return result;
    }

    /**
     * 显示物流信息
     * @param lendId
     * @return
     */
    @Override
    public DisplayLogistics findLogisticsByLendId(int lendId) {
        //首先更新物流信息
        DisplayLogistics displayLogistics = logisticsMapper.findLogisticsByLendId(lendId);
        String result =
                KDQuery.getOrderTracesByJson(displayLogistics.getCompanyNumber(),
                        displayLogistics.getNumber());
        String logistics_status = StringConverter.converterToString(
                StringConverter.converterToJsonNode(result), "State");
        logisticsMapper.updateLogisticsStatus(Integer.parseInt(logistics_status), displayLogistics.getId());
        //更新后查询最新物流信息返回
        return logisticsMapper.findLogisticsByLendId(lendId);
    }

    @Override
    public List<Company> findLogisticsCompany() {
        return logisticsMapper.findLogisticsCompany();
    }
}
