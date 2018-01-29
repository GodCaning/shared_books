package com.xust.wtc.Service.book;

import com.xust.wtc.Entity.Book;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2017/12/5.
 */
public interface BookService {

    /**
     * 根据点击率返回书籍信息
     * @return
     */
    List<Book> findTop10Book();

    /**
     * 根据ID查询书籍信息
     * @param id
     * @return
     */
    Book findBook(int id);

    /**
     * 根据字符串查询图书
     * @param content
     * @return
     */
    List<Book> searchBooks(String content);

    /**
     * 增加一本书籍
     * @param isbn
     * @param sessionId
     * @return
     */
    Result addBook(String isbn, String sessionId);

    /**
     * 根据输入文字返回匹配的书籍名称
     * @param content
     * @return
     */
    List<String> searchTitleToES(String content);
}
