package com.xust.wtc.Service.admin;

import com.github.pagehelper.PageInfo;
import com.xust.wtc.Entity.admin.BookInfo;
import com.xust.wtc.Entity.chat.Feedback;

/**
 * Created by Spirit on 2018/5/7.
 */
public interface AdminService {

    PageInfo<BookInfo> displayBookInfo(int page, int size);

    void deleteStock(int stockId);

    PageInfo<Feedback> displayFeedback(int currentPage, int pageSize);
}
