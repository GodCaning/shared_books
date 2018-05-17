package com.xust.wtc.Service.book;

import com.xust.wtc.Entity.book.Stock;
import com.xust.wtc.Entity.book.AllBook;
import com.xust.wtc.Entity.book.Book;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.book.UserBook;

import java.util.List;

/**
 * Created by Spirit on 2017/12/5.
 */
public interface BookService {

    /**
     * 根据书籍ID返回库存情况
     * @param bookID
     * @return
     */
    List<Stock> findBookStock(int bookID);

    /**
     * 根据点击率返回书籍信息
     * @return
     */
    List<Book> findTop10Book();

    /**
     * 根据isbn返回书籍信息
     * @param isbn
     * @return
     */
    Result queryBookByISBN(String isbn);

    /**
     * 根据书籍创建时间返回最近的书籍
     * @return
     */
    AllBook findBooksWithCreateTime(int currentPage, int pageSize);

    AllBook findBooksByCate(int currentPage, int pageSize, int cate);

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
     * @param userID
     * @return
     */
    Result addBook(String isbn, int userID);

    /**
     * 根据输入文字返回匹配的书籍名称
     * @param content
     * @return
     */
    List<String> searchTitleToES(String content);

    /**
     * 返回用户上传的书籍
     * @param userID
     * @return
     */
    List<UserBook> userBooks(int userID);
}
