package com.xust.wtc.Dao.book;

import com.xust.wtc.Entity.Book;
import org.springframework.stereotype.Repository;

/**
 * Created by Spirit on 2017/12/5.
 */
@Repository
public interface BookMapper {
    int addBook(Book book);
}
