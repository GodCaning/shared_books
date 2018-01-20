package com.xust.wtc.Service.book.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xust.wtc.Dao.book.BookMapper;
import com.xust.wtc.Dao.book.StockMapper;
import com.xust.wtc.Entity.Book;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.book.BookService;
import com.xust.wtc.utils.StringConverter;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
            JsonNode jsonNode = StringConverter.converterToJsonNode(bookResult);
            Map<String, JsonNode> map = StringConverter.jsonNodeToMap(jsonNode);

            String title = getValue(map, "title");
            String image = getValue(map, "image");
            String author = getValue(map, "author");
            String translator = getValue(map, "translator");
            String publisher = getValue(map, "publisher");
            String pubdate = getValue(map, "pubdate");
            String summary = getValue(map, "summary");
            String price = getValue(map, "price");
            book = new Book(isbn, title, image, author,
                    translator, publisher, pubdate, summary, price);
            //存入数据库
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
                .setQuery(QueryBuilders.termQuery("isbn", isbn))
                .execute().actionGet();
        SearchHits searchHits = response.getHits();
        return searchHits;
    }

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
    }
}
