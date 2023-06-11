package com.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@ToString
//由于懒加载导致的mybatis异常，解决方法添加@JsonIgnoreProperties(value = {"handler"})
@JsonIgnoreProperties(value = {"handler"})
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
}
