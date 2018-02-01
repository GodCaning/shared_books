package com.xust.wtc.Controller.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.xust.wtc.Entity.book.Book;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.book.UserBook;
import com.xust.wtc.Service.book.BookService;
import com.xust.wtc.utils.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Spirit on 2017/12/5.
 */
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 根据isbn返回书籍信息
     * @param isbn
     * @return
     */
    @GetMapping(value = "/queryisbn", consumes = "application/json", produces = "application/json")
    public Result queryBookByISBN(@RequestParam("isbn") String isbn) {
        return bookService.queryBookByISBN(isbn);
    }

    /**
     * 增加一本书籍
     * @param httpSession
     * @param isbn
     * @return
     */
    @PostMapping(value = "/addBook", consumes = "application/json", produces = "application/json")
    public Result addBook(HttpSession httpSession, @RequestBody String isbn) {
        JsonNode jsonNode = StringConverter.converterToJsonNode(isbn);
        String sessionId = httpSession.getId();
        return bookService.addBook(StringConverter.converterToString(jsonNode, "isbn"), sessionId);
    }

    /**
     * 显示书籍 根据书籍创建时间
     * @param currentPage
     * @param pageSize
     */
    @GetMapping(value = "/findBooks", consumes = "application/json", produces = "application/json")
    public List<Book> findBooksWithCreateTime(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                        @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        return bookService.findBooksWithCreateTime(currentPage, pageSize);
    }

    /**
     * 根据文本查询匹配书籍返回
     * @param content
     * @return
     */
    @GetMapping(value = "/searchBooks", consumes = "application/json", produces = "application/json")
    public List<Book> searchBooks(@RequestParam("content") String content) {
        return bookService.searchBooks(content);
    }

    /**
     * 根据输入文字返回匹配的书籍名称
     * @param content
     * @return
     */
    @GetMapping(value = "/searchTitle", consumes = "application/json", produces = "application/json")
    public List<String> searchTitleToES(@RequestParam("content") String content) {
        return bookService.searchTitleToES(content);
    }

    /**
     * 根据书籍ID查询书籍具体信息
     * @param id
     * @return
     */
    @GetMapping(value = "/findBook/{id}", consumes = "application/json", produces = "application/json")
    public Book findBookById(@PathVariable("id") int id) {
        return bookService.findBook(id);
    }

    /**
     * 根据点击率返回书籍信息
     * @return
     */
    @GetMapping(value = "/findTop10", consumes = "application/json", produces = "application/json")
    public List<Book> findTop10Book() {
        return bookService.findTop10Book();
    }

    /**
     * 返回用户上传的书籍
     * @return
     */
    @GetMapping(value = "/userBooks", consumes = "application/json", produces = "application/json")
    public List<UserBook> userBooks(HttpSession session) {
        return bookService.userBooks(session.getId());
    }
}
