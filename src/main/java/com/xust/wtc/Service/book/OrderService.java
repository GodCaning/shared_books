package com.xust.wtc.Service.book;

import com.xust.wtc.Entity.book.Lend;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.book.LenderInfo;
import com.xust.wtc.Entity.book.ReturnOrder;

import java.util.List;

/**
 * Created by Spirit on 2017/12/10.
 */
public interface OrderService {

    Result orderForBook(int userID, int stockId);

    List<Lend> getBorrowerOrders(int borrowerID);

    List<Lend> getLenderOrders(int lenderId);

    List<ReturnOrder> canBeReturnedBooks(int borrowerId);

    Lend getOrder(int id);

    Result borrowerModifyOrder(Lend lend);

    Result lenderModifyOrder(LenderInfo lend);

    Result lenderRefuseOrder(int id);
}
