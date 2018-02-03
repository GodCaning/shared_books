package com.xust.wtc.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xust.wtc.Entity.book.Book;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 当ES服务器监听使用内网服务器IP而访问使用外网IP时，
 * 不要使用client.transport.sniff为true，
 * 在自动发现时会使用内网IP进行通信，
 * 导致无法连接到ES服务器，
 * 而直接使用addTransportAddress方法进行指定ES服务器。
 *
 * Created by Spirit on 2017/12/6.
 */
public class ElasticSearchClient {

    public static void main(String[] args) {
        try {
            //创建客户端
            Settings settings = Settings.settingsBuilder()
//                    .put("client.transport.sniff",true)
                    .put("cluster.name", "wtc-application").build();
            Client client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName("193.112.4.174"), 9300));

//            createSharedBooks(client);
            createFastSearch(client);

            client.close();

//            SearchRequestBuilder requestBuilder =
//                    client.prepareSearch("shared_books").setTypes("book");
//            SearchResponse response = requestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                    .setQuery(QueryBuilders.termQuery("isbn", "9787111376613"))
//                    .execute().actionGet();
//            SearchHits searchHits = response.getHits();
//            SearchHit[] searchHits1 = searchHits.getHits();
//            for (int i = 0; i < searchHits1.length; i++) {
//                SearchHit s = searchHits1[i];
//                System.out.println(s.getSourceAsString());
//                String b = s.getSourceAsString();
//                Book book = new ObjectMapper().readValue(b, Book.class);
//                System.out.println(book);
//            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立书籍查找的ES库
     * @param client
     */
    private static void createSharedBooks(Client client) {
        //建立索引文档
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("settings")
                            .field("number_of_shards", 1)
                            .field("number_of_replicas", 0)
                        .endObject()

                        .startObject("mappings")
                            .startObject("book")
                                .startObject("properties")
                                    .startObject("id")
                                        .field("type", "integer")
                                    .endObject()
                                    .startObject("isbn")
                                        .field("type", "String")
                                        .field("index", "not_analyzed")
                                    .endObject()
                                    .startObject("title")
                                        .field("type", "String")
                                    .endObject()
                                    .startObject("image")
                                        .field("type", "String")
                                        .field("index", "no")
                                    .endObject()
                                    .startObject("author")
                                        .field("type", "String")
                                        .field("index", "not_analyzed")
                                    .endObject()
                                    .startObject("publisher")
                                        .field("type", "String")
                                        .field("index", "not_analyzed")
                                    .endObject()
                                    .startObject("pubdate")
                                        .field("type", "String")
                                        .field("index", "no")
                                    .endObject()
                                    .startObject("summary")
                                        .field("type", "String")
                                        .field("analyzer", "ik")
                                    .endObject()
                                    .startObject("price")
                                        .field("type", "String")
                                        .field("index", "no")
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            System.out.println(mapping.string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreateIndexRequestBuilder cirb =
                client.admin().indices().prepareCreate("shared_books").setSource(mapping);

        CreateIndexResponse response = cirb.execute().actionGet();
        if (response.isAcknowledged()) {
            System.out.println("创建成功");
        } else {
            System.out.println("创建失败");
        }
    }

    /**
     * 建立搜索补全的ES库
     * @param client
     */
    private static void createFastSearch(Client client) {
        //建立索引文档
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                        .startObject()
                            .startObject("settings")
                                .field("number_of_shards", 1)
                                .field("number_of_replicas", 0)
                                .startObject("analysis")
                                    .startObject("filter")
                                        .startObject("wtc_filter")
                                            .field("type", "edge_ngram")
                                            .field("min_gram", "1")
                                            .field("max_gram", "10")
                                        .endObject()
                                    .endObject()
                                    .startObject("analyzer")
                                        .startObject("wtc")
                                            .field("type", "custom")
                                            .field("tokenizer", "ik")
                                            .field("filter", "wtc_filter")
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()

                            .startObject("mappings")
                                .startObject("book_title")
                                    .startObject("properties")
                                        .startObject("title")
                                            .field("type", "String")
                                            .field("search_analyzer", "ik")
                                            .field("analyzer", "wtc")
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreateIndexRequestBuilder cirb =
                client.admin().indices().prepareCreate("fast_search").setSource(mapping);

        CreateIndexResponse response = cirb.execute().actionGet();
        if (response.isAcknowledged()) {
            System.out.println("创建成功");
        } else {
            System.out.println("创建失败");
        }
    }

}
