package com.xust.wtc.Service.book;

import com.xust.wtc.Entity.Result;

/**
 * Created by Spirit on 2017/12/5.
 */
public interface BookService {
    Result addBook(String isbn, String sessionId);
}
