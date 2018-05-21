package com.xust.wtc.Entity.book;

import java.util.List;

/**
 * 显示评论信息
 * Created by Spirit on 2018/1/10.
 */
public class DisplayComment {

    private int id;

    private int bookId;

    private int commentPersonId;

    private String commentPersonName;

    private int parentId;

    private String content;

    private String commentTime;

    private String portrait;

    private List<Reply> replies;

    public DisplayComment() {
    }

    public DisplayComment(int id, int bookId, int commentPersonId, String commentPersonName,
                          int parentId, String content, String commentTime, String portrait, List<Reply> replies) {
        this.id = id;
        this.bookId = bookId;
        this.commentPersonId = commentPersonId;
        this.commentPersonName = commentPersonName;
        this.parentId = parentId;
        this.content = content;
        this.commentTime = commentTime;
        this.portrait = portrait;
        this.replies = replies;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "DisplayComment{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", commentPersonId=" + commentPersonId +
                ", commentPersonName='" + commentPersonName + '\'' +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", replies=" + replies +
                '}';
    }
}
