package com.xust.wtc.Controller.logistics;

import com.xust.wtc.Entity.Company;
import com.xust.wtc.Entity.DisplayLogistics;
import com.xust.wtc.Entity.Logistics;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.logistics.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物流控制类
 * Created by Spirit on 2018/1/9.
 */
@RestController
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    /**
     * 修改物流信息
     * @param logistics
     * @return
     */
    @PostMapping(value = "/updateLogistics", consumes = "application/json", produces = "application/json")
    public Result updateLogistics(@RequestBody Logistics logistics) {
        return logisticsService.updateLogistics(logistics);
    }

    /**
     * 根据订单ID获取物流信息
     * @param lendId
     * @return
     */
    @GetMapping(value = "/findLogistics", consumes = "application/json", produces = "application/json")
    public DisplayLogistics findLogisticsByLendId(@PathVariable int lendId) {
        return logisticsService.findLogisticsByLendId(lendId);
    }

    /**
     * 返回快递公司信息
     * @return
     */
    @GetMapping(value = "/findLogisticsCompany", consumes = "application/json", produces = "application/json")
    public List<Company> findLogisticsCompany() {
        return logisticsService.findLogisticsCompany();
    }

}
