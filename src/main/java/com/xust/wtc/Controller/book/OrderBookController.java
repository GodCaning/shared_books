package com.xust.wtc.Controller.book;

import com.xust.wtc.Entity.Lend;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.book.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单控制类
 * Created by Spirit on 2017/12/10.
 */
@RestController
public class OrderBookController {

    @Autowired
    private OrderService orderService;

    /**
     * 借书人下订单
     * @param httpSession
     * @param stockId
     * @return
     */
    @PostMapping(value = "/orderForBook", consumes = "application/json", produces = "application/json")
    public Result orderForBook(HttpSession httpSession, @RequestBody int stockId) {
        String sessionId = httpSession.getId();
        return orderService.orderForBook(sessionId, stockId);
    }

    /**
     * 获取借书订单信息
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/borrowerOrders", consumes = "application/json", produces = "application/json")
    public List<Lend> getBorrowerOrders(HttpSession httpSession) {
        String sessionId = httpSession.getId();
        return orderService.getBorrowerOrders(sessionId);
    }

    /**
     * 获取出借订单信息
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/lenderOrders", consumes = "application/json", produces = "application/json")
    public List<Lend> getLenderOrders(HttpSession httpSession) {
        String sessionId = httpSession.getId();
        return orderService.getLenderOrders(sessionId);
    }

    /**
     * 根据订单ID获取订单信息
     * @param lendId
     * @return
     */
    @GetMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public Lend getOrder(@RequestParam(value = "lendId") int lendId) {
        return orderService.getOrder(lendId);
    }

    /**
     * 修改借书人信息
     * @param lend
     * @return
     */
    @PutMapping(value = "/borrowerModifyOrder", consumes = "application/json", produces = "application/json")
    public Result borrowerModifyOrder(@RequestBody Lend lend) {
        return orderService.borrowerModifyOrder(lend);
    }

    /**
     * 修改出借人信息
     * @param lend
     * @return
     */
    @PutMapping(value = "/lenderModifyOrder", consumes = "application/json", produces = "application/json")
    public Result lenderModifyOrder(@RequestBody Lend lend) {
        return orderService.lenderModifyOrder(lend);
    }

    /**
     * 出借人根据ID拒绝借书
     * @param id
     * @return
     */
    @GetMapping(value = "/refuseOrder/{id}", consumes = "application/json", produces = "application/json")
    public Result lenderRefuseOrder(@PathVariable int id) {
        return orderService.lenderRefuseOrder(id);
    }
}
