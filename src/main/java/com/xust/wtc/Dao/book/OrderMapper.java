package com.xust.wtc.Dao.book;

import com.xust.wtc.Entity.book.Lend;
import com.xust.wtc.Entity.book.LenderInfo;
import com.xust.wtc.Entity.book.ReturnOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Spirit on 2017/12/10.
 */
@Repository
public interface OrderMapper {

    /**
     * 下订单时先修改库存信息
     * @param stockId   库存ID
     * @return
     */
    int orderForBook(@Param("stockId")int stockId);

    /**
     * 生成一张订单表
     * @param borrowerId 下订单人的ID
     * @param stockId    书籍库存ID
     * @return
     */
    int insertLendRecord(@Param("borrowerId")int borrowerId, @Param("stockId")int stockId);

    /**
     * 获取借书人订单信息
     * @param borrowerId
     * @return
     */
    List<Lend> getBorrowerOrders(@Param("borrowerId") int borrowerId);

    /**
     * 获取出借人订单信息
     * @param lenderId
     * @return
     */
    List<Lend> getLenderOrders(@Param("lenderId") int lenderId);

    /**
     * 根据订单ID获取一个订单信息
     * @param id
     * @return
     */
    Lend getOrder(@Param("id") int id);

    /**
     * 修改订单中借书人信息
     * @param lend
     * @return
     */
    int borrowerModifyOrder(Lend lend);

    /**
     * 修改订单中出借人信息
     * @param lend
     * @return
     */
    int lenderModifyOrder(LenderInfo lend);

    /**
     * 出借人拒绝订单
     * @param id
     * @return
     */
    void lenderRefuseOrder(@Param("id")int id);

    /**
     * 可归还的书籍订单
     * @return
     */
    List<ReturnOrder> canBeReturnedBooks(@Param("borrowerId") int borrowerId);

    /**
     * 获取过期库存ID
     * @param timestamp
     * @return
     */
    List<Integer> getExpireOrder(@Param("time") Timestamp timestamp);

    /**
     * 取消库存锁定
     * @param list
     * @return
     */
    int cancelStock(@Param("list") List<Integer> list);

    /**
     * 取消订单
     * @param list
     * @return
     */
    int cancelLend(@Param("list") List<Integer> list);
}
