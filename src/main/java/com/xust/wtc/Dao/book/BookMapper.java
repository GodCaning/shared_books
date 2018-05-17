package com.xust.wtc.Dao.book;

import com.xust.wtc.Entity.book.Book;
import com.xust.wtc.Entity.book.UserBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2017/12/5.
 */
@Repository
public interface BookMapper {

    /**
     * 返回用户上传的书籍
     * @param id
     * @return
     */
    List<UserBook> userBooks(@Param("id") int id);

    /**
     * 根据点击率返回书籍信息
     * @return
     */
    List<Book> findTop10Book();

    /**
     * 根据书籍创建时间返回最近的书籍
     * @return
     */
    List<Book> findBooksWithCreateTime();

    List<Book> findBooksByCate(@Param("tag") String tag);

    /**
     * 根据书籍ID查找书籍
     * @param bookId
     * @return
     */
    Book findBook(@Param("bookId") int bookId);

    /**
     * 增加一本书籍信息
     * @param book
     * @return
     */
    int addBook(Book book);

    /**
     * 通过书籍ID增加书籍点击率
     * @param bookId
     * @return
     */
    int updateBookCTR(@Param("bookId") int bookId);
}
