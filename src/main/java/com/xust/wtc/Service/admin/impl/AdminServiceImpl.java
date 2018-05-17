package com.xust.wtc.Service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xust.wtc.Dao.admin.AdminMapper;
import com.xust.wtc.Entity.admin.BookInfo;
import com.xust.wtc.Entity.chat.Feedback;
import com.xust.wtc.Service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Spirit on 2018/5/7.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public PageInfo<BookInfo> displayBookInfo(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<BookInfo> bookList = adminMapper.findBookInfo();
        PageInfo<BookInfo> pageInfo = new PageInfo<>(bookList);
        return pageInfo;
    }

    public void deleteStock(int stockId) {
        adminMapper.deleteStock(stockId);
    }

    public PageInfo<Feedback> displayFeedback(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Feedback> feedbackList = adminMapper.findFeedbackInfo();
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbackList);
        return pageInfo;
    }
}
