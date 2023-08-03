package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
@Component
public class Order {
    @TableId("order_id")
    private Integer orderId;
    private Integer bookNum;
    private Integer userId;
    private Integer bookId;
    private Timestamp orderTime;
    private String orderStatus;
    @TableField(select = false)
    private User user;
    @TableField(select = false)
    private Book book;

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookNum() {
        return bookNum;
    }

    public void setBookNum(Integer bookNum) {
        this.bookNum = bookNum;
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

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order(Integer orderId, Integer bookNum, Integer userId, Integer bookId, Timestamp orderTime, String orderStatus, User user, Book book) {
        this.orderId = orderId;
        this.bookNum = bookNum;
        this.userId = userId;
        this.bookId = bookId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.user = user;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", bookNum=" + bookNum +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", orderTime=" + orderTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
