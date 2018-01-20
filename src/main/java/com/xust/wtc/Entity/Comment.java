package com.xust.wtc.Entity;

import java.sql.Timestamp;

/**
 * Created by Spirit on 2018/1/10.
 */
public class Comment {

    private int id;

    private int bookId;

    private int commentPersonId;

    private String commentPersonName;

    private int parentId;

    private int commentType;

    private String content;

    private Timestamp timestamp;

    public Comment() {
    }

    public Comment(int id, int bookId, int commentPersonId, String commentPersonName, Integer parentId, int commentType, String content, Timestamp timestamp) {
        this.id = id;
        this.bookId = bookId;
        this.commentPersonId = commentPersonId;
        this.commentPersonName = commentPersonName;
        this.parentId = parentId;
        this.commentType = commentType;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCommentPersonId() {
        return commentPersonId;
    }

    public void setCommentPersonId(int commentPersonId) {
        this.commentPersonId = commentPersonId;
    }

    public String getCommentPersonName() {
        return commentPersonName;
    }

    public void setCommentPersonName(String commentPersonName) {
        this.commentPersonName = commentPersonName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
