package com.xust.wtc.Service.book.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xust.wtc.Dao.book.BookMapper;
import com.xust.wtc.Dao.book.StockMapper;
import com.xust.wtc.Entity.book.Book;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.book.UserBook;
import com.xust.wtc.Service.book.BookService;
import com.xust.wtc.utils.StringConverter;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Spirit on 2017/12/5.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Client client;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private StockMapper stockMapper;

    private String url = "https://api.douban.com/v2/book/isbn/{isbn}";

    /**
     * 返回用户上传的书籍
     * @param sessionId
     * @return
     */
    @Override
    public List<UserBook> userBooks(String sessionId) {
        Integer userId = (Integer)redisTemplate.opsForValue().get(sessionId);
        return bookMapper.userBooks(userId);
    }

    /**
     * 根据isbn查找书籍并返回
     * @param isbn
     * @return
     */
    @Override
    public Result queryBookByISBN(String isbn) {
        Result result = new Result();
        try {
            Book book = getBook(restTemplate.getForObject(url, String.class, isbn));
            book.setIsbn(isbn);
            result.setStatus(1);
            result.setContent(book);
        } catch (Exception e) {
            result.setStatus(0);
            result.setContent("暂未此条形码的书籍信息，请以后在试。");
        }
        return result;
    }

    /**
     * 根据文本查询匹配书籍返回
     * @param content
     * @return
     */
    @Override
    public List<Book> searchBooks(String content) {
        SearchResponse response = client.prepareSearch("shared_books").setTypes("book")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(multiMatchQuery(content, "title", "summary"))
                .execute().actionGet();
        List<Book> bookList = new ArrayList<>();
        for (SearchHit searchHit : response.getHits().getHits()) {
            System.out.println(searchHit.getSourceAsString());
            bookList.add(StringConverter.stringToBook(searchHit.getSourceAsString()));
        }
        return bookList;
    }

    /**
     * 存入一本书
     * @param isbn
     * @param sessionId
     * @return
     */
    @Override
    @Transactional
    public Result addBook(String isbn, String sessionId) {
        Result result = new Result();
        //先搜索是否存在ES中
        Book book = null;
        SearchHits searchHits = existsES(isbn);
        if (searchHits.getTotalHits() < 1) {
            //不存在ES则把书籍信息存入ES和BOOK表中
            String bookResult = restTemplate.getForObject(url, String.class, isbn);
            //解析result
            System.out.println(bookResult);
            book = getBook(bookResult);
            book.setIsbn(isbn);
            //存入书籍信息数据库
            bookMapper.addBook(book);
            System.out.println(book);
            //存入ES
            addBookToES(book);
        } else if (searchHits.getTotalHits() == 1){
            SearchHit hit = searchHits.getHits()[0];
            book = StringConverter.stringToBook(hit.getSourceAsString());
        } else {
            //todo 抛出异常
        }

        Integer userId = (Integer) redisTemplate.opsForValue().get(sessionId);
        stockMapper.addStock(userId, book.getId());

        result.setStatus(1);
        result.setContent("增加成功");
        return result;
    }

    /**
     * 根据查找的结果解析后返回书籍
     * @param bookString
     * @return
     */
    private Book getBook(String bookString) {
        JsonNode jsonNode = StringConverter.converterToJsonNode(bookString);
        Map<String, JsonNode> map = StringConverter.jsonNodeToMap(jsonNode);

        String title = getValue(map, "title");
        String image = getValue(map, "image");
        String author = getValue(map, "author");
        String translator = getValue(map, "translator");
        String publisher = getValue(map, "publisher");
        String pubdate = getValue(map, "pubdate");
        String summary = getValue(map, "summary");
        String price = getValue(map, "price");
        Book book = new Book(title, image, author,
                translator, publisher, pubdate, summary, price);
        return book;
    }

    private String getValue(Map<String, JsonNode> map, String name) {
        String result = StringConverter.converterToString(map, name);
        if (StringUtils.isEmpty(result)) {
            result = StringConverter.arrayConverterToString(map, name);
            if (StringUtils.isEmpty(result)) {
                //TODO 抛出异常
            }
        }
        return result;
    }

    /**
     * 是否存在当前isbn的文档
     * @param isbn
     * @return
     */
    private SearchHits existsES(String isbn) {
        SearchRequestBuilder requestBuilder =
                client.prepareSearch("shared_books").setTypes("book");
        SearchResponse response = requestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(termQuery("isbn", isbn))
                .execute().actionGet();
        SearchHits searchHits = response.getHits();
        return searchHits;
    }

    /**
     * 在ES中增加一个图书信息
     * @param book
     */
    private void addBookToES(Book book) {

        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("id", book.getId())
                        .field("isbn", book.getIsbn())
                        .field("title", book.getTitle())
                        .field("image", book.getImage())
                        .field("author", book.getAuthor())
                        .field("translator", book.getTranslator())
                        .field("publisher", book.getPublisher())
                        .field("pubdate", book.getPubdate())
                        .field("summary", book.getSummary())
                        .field("price", book.getPrice())
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.prepareIndex("shared_books", "book")
                .setSource(mapping).get();
        client.prepareSearch("fast_search", "book_title")
                .setSource("{" + "\"title\":" + book.getTitle() +"}").get();
    }

    /**
     * 根据输入文字返回匹配的书籍名称
     * @param content
     * @return
     */
    @Override
    public List<String> searchTitleToES(String content) {
        SearchResponse response = client.prepareSearch("fast_search")
                .setTypes("book_title").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(termQuery("title", content)) // Query
                .setFrom(0).setSize(6).setExplain(true)
                .execute().actionGet();
        System.out.println(content);
        List<String> searchTiler = new ArrayList<>();
        SearchHit []searchHits = response.getHits().getHits();
        System.out.println(searchHits.length);
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsString());
            searchTiler.add(searchHit.getSourceAsString());
        }
        return searchTiler;
    }

    /**
     * 根据书籍ID查找书籍
     * @param id
     * @return
     */
    @Override
    public Book findBook(int id) {
        //增加书籍点击率
        bookMapper.updateBookCTR(id);
        return bookMapper.findBook(id);
    }

    /**
     * 根据点击率返回TOP10
     * @return
     */
    @Override
    public List<Book> findTop10Book() {
        return bookMapper.findTop10Book();
    }

    /**
     * 根据书籍创建时间返回最近的书籍
     * @return
     */
    @Override
    public List<Book> findBooksWithCreateTime(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Book> bookList = bookMapper.findBooksWithCreateTime();
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);
        System.out.println(pageInfo.getFirstPage());//第一页的页码
        System.out.println(pageInfo.getLastPage());//最后一页的页码
        System.out.println(pageInfo.getPageNum()); //当前页的坐标
        System.out.println(pageInfo.getPages()); //总页数
        System.out.println(pageInfo.getPageSize()); //当前一页的长度
        System.out.println(pageInfo.getTotal());  //总条数
        return null;
    }
}
