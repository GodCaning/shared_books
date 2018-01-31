package com.xust.wtc.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xust.wtc.Entity.book.Book;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * JsonNode节点说明
 * 1）fieldNames() 获取此节点所有子节点的名称Key
 * 2）elements() 获取次节点所有子节点的值Value
 * 3）fields() 获取次节点的所有子节点的迭代集合(K, V)
 * Created by Spirit on 2017/12/5.
 */
public class StringConverter {

    private StringConverter(){}

    /**
     * JSON字符串转为Book对象
     * @param json
     * @return
     */
    public static Book stringToBook(String json) {
        Book book = null;
        ObjectMapper objectMapper = Converter.INSTANCE.getObjectMapper();
        try {
            book = objectMapper.readValue(json, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return book;
        }
    }

    /**
     * JsonNode对象转为HashMap，从一级JsonNode节点中获取信息
     * @param jsonNode
     * @return
     */
    public static Map<String, JsonNode> jsonNodeToMap(JsonNode jsonNode) {
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        Map<String, JsonNode> map = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> m = iterator.next();
            map.put(m.getKey(), m.getValue());
        }
        return map;
    }

    /**
     * 从JsonNode对象中取出相应的值
     * @param jsonNode
     * @param name
     * @return
     */
    public static String converterToString(JsonNode jsonNode, String name) {
        return jsonNode.findValue(name).asText();
    }

    /**
     * 从HashMap中获取一级JsonNode节点信息
     * @param map
     * @param name
     * @return
     */
    public static String converterToString(Map<String, JsonNode> map, String name) {
        return map.get(name).asText();
    }

    /**
     * 从JsonNode对象中取出相应数组中的第一个值
     * @param jsonNode
     * @param name
     * @return
     */
    public static String arrayConverterToString(JsonNode jsonNode, String name) {
        Iterator<JsonNode> i = jsonNode.findValue(name).elements();
        if (i.hasNext()) {
            return i.next().asText();
        } else {
            return "";
        }
    }

    /**
     * 从HashMap中获取一级JsonNode数组节点第一条信息
     * @param map
     * @param name
     * @return
     */
    public static String arrayConverterToString(Map<String, JsonNode> map, String name) {
        Iterator<JsonNode> i = map.get(name).elements();
        if (i.hasNext()) {
            return i.next().asText();
        } else {
            return "";
        }
    }

    /**
     * 把JSON字符串解析为JsonNode对象
     * @param json
     * @return
     */
    public static JsonNode converterToJsonNode(String json) {
        ObjectMapper objectMapper = Converter.INSTANCE.getObjectMapper();
        JsonNode result = null;
        try {
            result = objectMapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 枚举实现线程安全的创建单例ObjectMapper
     */
    private enum Converter {

        INSTANCE;

        private ObjectMapper objectMapper;

        Converter() {
            objectMapper = new ObjectMapper();
        }

        public ObjectMapper getObjectMapper() {
            return objectMapper;
        }
    }
}
