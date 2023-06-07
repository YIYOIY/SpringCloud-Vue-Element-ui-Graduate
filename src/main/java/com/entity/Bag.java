package com.entity;

import java.sql.Date;

public class Bag {
    private Integer bagId;
    private Integer bookNumber;
    private Integer userId;
    private Integer bookId;
    private String bookName;
    private String userName;
    private Double bookPrice;
    private String bookPicture;
    private Double totalPrice;

    private Date addTime;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookPicture() {
        return bookPicture;
    }

    public void setBookPicture(String bookPicture) {
        this.bookPicture = bookPicture;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public Bag(Integer bagId, Integer bookNumber, Integer userId, Integer bookId, String bookName, Double bookPrice, String bookPicture, Date addTime) {
        this.bagId = bagId;
        this.bookNumber = bookNumber;
        this.userId = userId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookPicture = bookPicture;
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "bagId=" + bagId +
                ", bookNumber=" + bookNumber +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookPicture='" + bookPicture + '\'' +
                ", totalPrice=" + totalPrice +
                ", addTime=" + addTime +
                '}';
    }

    public Bag(Integer bagId, Integer bookNumber, Integer userId, Integer bookId, String bookName, Double bookPrice, String bookPicture, Double totalPrice, Date addTime) {
        this.bagId = bagId;
        this.bookNumber = bookNumber;
        this.userId = userId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookPicture = bookPicture;
        this.totalPrice = totalPrice;
        this.addTime = addTime;
    }

    public Bag() {

    }

    public Bag(Integer bagId, Integer bookNumber, Integer userId, Integer bookId, Date addTime) {
        this.bagId = bagId;
        this.bookNumber = bookNumber;
        this.userId = userId;
        this.bookId = bookId;
        this.addTime = addTime;
    }

    public Bag(Integer bookNumber, Integer userId, Integer bookId) {
        this.bookNumber = bookNumber;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Integer getBagId() {
        return bagId;
    }

    public void setBagId(Integer bagId) {
        this.bagId = bagId;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
