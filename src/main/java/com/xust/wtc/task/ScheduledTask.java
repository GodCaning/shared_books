package com.xust.wtc.task;

import com.xust.wtc.Dao.book.OrderMapper;
import com.xust.wtc.Dao.logistics.LogisticsMapper;
import com.xust.wtc.Entity.logistics.DisplayLogistics;
import com.xust.wtc.Entity.logistics.LogisticsAll;
import com.xust.wtc.utils.KDQuery;
import com.xust.wtc.utils.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Spirit on 2017/12/10.
 */
@Component
public class ScheduledTask {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private LogisticsMapper logisticsMapper;

    /**
     * 每过5分钟 删除一次订单时间超过15分钟未确认订单
     */
    @Scheduled(fixedRate = 300000)
    public void cancelExpireOrder() {
        long curr = System.currentTimeMillis();
        long time = curr - 900000;
        Timestamp timestamp = new Timestamp(time);
        //根据时间删除订单
        List<Integer> expireOrders =  orderMapper.getExpireOrder(timestamp);
        if (expireOrders.size() > 0) {
            orderMapper.cancelStock(expireOrders);
            orderMapper.cancelLend(expireOrders);
        }
    }

    /**
     * 每天早上10点和晚上10点更新物流信息
     */
    @Scheduled(cron="0 0 10,22 * * ? ")
    public void updateLogisticsStatus() {
        for (LogisticsAll logisticsAll : logisticsMapper.findAllLogistics()) {
            if (logisticsAll.getStatus() == 2 && logisticsAll.getLogisticsStatus() == 3) {
                //当物流为归还中并且为签收状态，改变该书籍所有状态
                logisticsMapper.updateStatus(logisticsAll.getId());
            } else {
                String result =
                        KDQuery.getOrderTracesByJson(logisticsAll.getCompanyNumber(),
                                logisticsAll.getNumber());
                String logistics_status = StringConverter.converterToString(
                        StringConverter.converterToJsonNode(result), "State");
                logisticsMapper.updateLogisticsStatus(Integer.parseInt(logistics_status), logisticsAll.getId());
            }

        }
    }

}
