package com.xust.wtc.Dao.book;

import com.xust.wtc.Entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2017/12/10.
 */
@Repository
public interface StockMapper {
    int addStock(@Param("personId")int personId, @Param("bookId")int bookId);

    List<Stock> findBookStock(@Param("id")int bookId);

}
