package com.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@ToString
public class Book {
    private Integer bookId;
    private Integer bookNo;
    private String seriesName;
    private Double bookPrice;
    private String bookName;
    private String bookAuthor;
    private String bookPicture;
    private String bookFactory;
    private Date bookAddDate;

    public Book() {
    }

    public Book(Integer bookNo, String seriesName, double bookPrice, String bookName, String bookAuthor, String bookPicture, String bookFactory, Date bookAddDate) {
        this.bookNo = bookNo;
        this.seriesName = seriesName;
        this.bookPrice = bookPrice;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPicture = bookPicture;
        this.bookFactory = bookFactory;
        this.bookAddDate = bookAddDate;
    }

    public Book(Integer bookId, Integer bookNo, String seriesName, double bookPrice, String bookName, String bookAuthor, String bookPicture, String bookFactory, Date bookAddDate) {
        this.bookId = bookId;
        this.bookNo = bookNo;
        this.seriesName = seriesName;
        this.bookPrice = bookPrice;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPicture = bookPicture;
        this.bookFactory = bookFactory;
        this.bookAddDate = bookAddDate;
    }

    public Book(Integer bookNo, String seriesName, Double bookPrice, String bookName, String bookAuthor, String bookPicture, String bookFactory) {
        this.bookNo = bookNo;
        this.seriesName = seriesName;
        this.bookPrice = bookPrice;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPicture = bookPicture;
        this.bookFactory = bookFactory;
    }

    public Book(String seriesName, String bookName, String bookAuthor, String bookPicture, String bookFactory) {
        this.seriesName = seriesName;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPicture = bookPicture;
        this.bookFactory = bookFactory;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String bookSeries) {
        this.seriesName = bookSeries;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPicture() {
        return bookPicture;
    }

    public void setBookPicture(String bookPicture) {
        this.bookPicture = bookPicture;
    }

    public String getBookFactory() {
        return bookFactory;
    }

    public void setBookFactory(String bookFactory) {
        this.bookFactory = bookFactory;
    }

    public Date getBookAddDate() {
        return bookAddDate;
    }

    public void setBookAddDate(Date bookAddDate) {
        this.bookAddDate = bookAddDate;
    }
}
