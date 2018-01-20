package com.xust.wtc.Service.book.impl;

import com.xust.wtc.Dao.book.OrderMapper;
import com.xust.wtc.Dao.logistics.LogisticsMapper;
import com.xust.wtc.Entity.Lend;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.book.OrderService;
import com.xust.wtc.Service.logistics.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Spirit on 2017/12/10.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 下订单
     * @param sessionId
     * @param stockId
     * @return
     */
    @Override
    @Transactional
    public Result orderForBook(String sessionId, int stockId) {
        Result result = new Result();
        int num = orderMapper.orderForBook(stockId);
        if (num > 0) {
            //修改库存信息成功,继续生成订单记录
            Integer userId = (Integer) redisTemplate.boundValueOps(sessionId).get();
            if (orderMapper.insertLendRecord(userId, stockId) > 0) {
                result.setStatus(1);
                result.setContent("操作成功");
            }
        } else {
            result.setStatus(0);
            result.setContent("库存不足");
        }

        return result;
    }

    /**
     * 获取借书订单
     * @param sessionId
     * @return
     */
    @Override
    public List<Lend> getBorrowerOrders(String sessionId) {
        Integer borrowerId = (Integer) redisTemplate.boundValueOps(sessionId).get();
        return orderMapper.getBorrowerOrders(borrowerId);
    }

    /**
     * 获取出借订单
     * @param sessionId
     * @return
     */
    @Override
    public List<Lend> getLenderOrders(String sessionId) {
        Integer lenderId = (Integer) redisTemplate.boundValueOps(sessionId).get();
        return orderMapper.getLenderOrders(lenderId);
    }

    /**
     * 根据ID获取订单
     * @param id
     * @return
     */
    @Override
    public Lend getOrder(int id) {
        return orderMapper.getOrder(id);
    }

    /**
     * 修改借书人信息
     * @param lend
     * @return
     */
    @Override
    public Result borrowerModifyOrder(Lend lend) {
        Result result = new Result();
        if (orderMapper.borrowerModifyOrder(lend) > 0) {
            result.setStatus(1);
            result.setContent("修改成功");
        } else {
            result.setStatus(0);
            result.setContent("修改失败");
        }
        return result;
    }

    /**
     * 修改出借人信息
     * @param lend
     * @return
     */
    @Override
    public Result lenderModifyOrder(Lend lend) {
        Result result = new Result();
        if (orderMapper.lenderModifyOrder(lend) > 0) {
            result.setStatus(1);
            result.setContent("修改成功");
            //生成物流信息
            logisticsService.addLogistics(lend.getId());
        } else {
            result.setStatus(0);
            result.setContent("修改失败");
        }
        return result;
    }

    /**
     * 借书人拒绝借书
     * @param id
     * @return
     */
    @Override
    public Result lenderRefuseOrder(int id) {
        Result result = new Result();
        if (orderMapper.lenderRefuseOrder(id) > 0) {
            result.setStatus(1);
            result.setContent("拒绝成功");
        } else {
            result.setStatus(0);
            result.setContent("拒绝失败");
        }
        return result;
    }
}
