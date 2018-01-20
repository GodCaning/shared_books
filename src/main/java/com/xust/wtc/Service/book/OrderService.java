package com.xust.wtc.Service.book;

import com.xust.wtc.Entity.Lend;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2017/12/10.
 */
public interface OrderService {

    Result orderForBook(String sessionId, int stockId);

    List<Lend> getBorrowerOrders(String sessionId);

    List<Lend> getLenderOrders(String sessionId);

    Lend getOrder(int id);

    Result borrowerModifyOrder(Lend lend);

    Result lenderModifyOrder(Lend lend);

    Result lenderRefuseOrder(int id);
}
