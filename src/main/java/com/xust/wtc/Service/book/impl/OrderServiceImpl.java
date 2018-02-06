package com.xust.wtc.Service.book.impl;

import com.xust.wtc.Dao.book.OrderMapper;
import com.xust.wtc.Dao.book.StockMapper;
import com.xust.wtc.Entity.book.Lend;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.book.LenderInfo;
import com.xust.wtc.Service.book.OrderService;
import com.xust.wtc.Service.logistics.LogisticsService;
import com.xust.wtc.utils.CONSTANT_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StockMapper stockMapper;

    /**
     * 下订单
     * @param userID
     * @param stockId
     * @return
     */
    @Override
    @Transactional
    public Result orderForBook(int userID, int stockId) {
        Result result = new Result();
        int num = orderMapper.orderForBook(stockId);
        int lend = orderMapper.insertLendRecord(userID, stockId);
        if ((num + lend) > 1) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("操作成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("库存不足");
        }
        return result;
    }

    /**
     * 获取借书订单
     * @param borrowerID
     * @return
     */
    @Override
    public List<Lend> getBorrowerOrders(int borrowerID) {
        return orderMapper.getBorrowerOrders(borrowerID);
    }

    /**
     * 获取出借订单
     * @param lenderId
     * @return
     */
    @Override
    public List<Lend> getLenderOrders(int lenderId) {
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
    @Transactional
    public Result borrowerModifyOrder(Lend lend) {
        Result result = new Result();
        int order = orderMapper.borrowerModifyOrder(lend);
        int stock = stockMapper.updateStockStatus(lend.getStockId());
        if ((order + stock) > 1) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("修改成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
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
    public Result lenderModifyOrder(LenderInfo lend) {
        Result result = new Result();
        int order = orderMapper.lenderModifyOrder(lend);
        int logistics = logisticsService.addLogistics(lend);
        if ((order+logistics) > 1) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("发货成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("发货失败");
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
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("拒绝成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("拒绝失败");
        }
        return result;
    }
}
