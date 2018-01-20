package com.xust.wtc.Dao.book;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Spirit on 2017/12/10.
 */
@Repository
public interface StockMapper {
    int addStock(@Param("personId")int personId, @Param("bookId")int bookId);
}
