package com.xust.wtc.Entity.admin;

/**
 * Created by Spirit on 2018/5/7.
 */
public class BookInfo {

    private int bookStockId;

    private String bookName;

    private String author;

    private String userName;

    private String stockStatus;

    private int status;

    public int getBookStockId() {
        return bookStockId;
    }

    public void setBookStockId(int bookStockId) {
        this.bookStockId = bookStockId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookStockId=" + bookStockId +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", userName='" + userName + '\'' +
                ", stockStatus='" + stockStatus + '\'' +
                ", status=" + status +
                '}';
    }
}
