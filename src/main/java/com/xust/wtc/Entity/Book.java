package com.xust.wtc.Entity;

/**
 * Created by Spirit on 2017/12/5.
 */
public class Book {
    private int id;
    private String isbn;
    private String title;
    private String image;
    private String author;
    private String translator;
    private String publisher;
    private String pubdate;
    private String summary;
    private String price;
    private int ctr;

    public Book() {}

    public Book(String isbn, String title, String image, String author, String translator, String publisher, String pubdate, String summary, String price) {
        this.isbn = isbn;
        this.title = title;
        this.image = image;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.summary = summary;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", translator='" + translator + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public int getCtr() {
        return ctr;
    }

    public void setCtr(int ctr) {
        this.ctr = ctr;
    }
}
