package com.xust.wtc.task;

import com.xust.wtc.Dao.book.OrderMapper;
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

    /**
     * 每过5分钟 删除一次订单时间超过15分钟未确认订单
     */
    @Scheduled(fixedRate = 300000)
    public void fixTimeExecute() {
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

}
