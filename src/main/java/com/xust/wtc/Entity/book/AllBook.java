package com.xust.wtc.Entity.book;

import java.util.List;

/**
 * Created by Spirit on 2018/2/3.
 */
public class AllBook {

    private int pages; //总页数
    private long total; //总条数
    private int pageNum; //当前页的坐标
    private int pageSize;
    private List<Book> list;
    private int cate;

    public AllBook() {
    }

    public AllBook(int pages, long total, int pageNum, int pageSize, List<Book> list) {
        this.pages = pages;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }
}
