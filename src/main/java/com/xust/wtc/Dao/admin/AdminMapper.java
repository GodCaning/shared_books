package com.xust.wtc.Dao.admin;

import com.xust.wtc.Entity.admin.BookInfo;
import com.xust.wtc.Entity.chat.Feedback;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2018/5/7.
 */
@Repository
public interface AdminMapper {

    List<BookInfo> findBookInfo();

    List<Feedback> findFeedbackInfo();

    int deleteStock(@Param("id")int stockId);
}
