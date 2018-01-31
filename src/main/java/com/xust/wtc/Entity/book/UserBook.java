package com.xust.wtc.Entity.book;

/**
 * Created by Spirit on 2018/1/30.
 */
public class UserBook {

    private int id;
    private String title;
    private String image;
    private String status;

    public UserBook() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                '}';
    }
}
