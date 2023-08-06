package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.yoi.enumvalue.CommentEnum;
import com.yoi.enumvalue.OrderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Validated
@ToString
@Data
@NoArgsConstructor
public class Order {
    @NotNull(message = "id不能为空")
    @TableId
    private Long id;
    private Long bookId;
    private Long userId;
    private Long wordId;

    private CommentEnum commentStatus;
    private Double bookPrice;
    private Double discount;
    private Double expressFare;
    private Double kickback;

    private LocalDateTime orderSignTime;
    private LocalDateTime buyTime;
    private LocalDateTime backTime;
    private LocalDateTime confirmTime;

    private OrderEnum orderStatus;
    private Integer buyNumber;
    @Version
    private LocalDateTime version;
    @TableLogic(value = "null",delval = "now()")
    private LocalDateTime flag;

    @TableField(exist = false)
    private Book book;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Word word;

    public Order(Long id, Long bookId, Long userId, Long wordId, CommentEnum commentStatus, Double bookPrice, Double discount, Double expressFare, Double kickback, LocalDateTime orderSignTime, LocalDateTime buyTime, LocalDateTime backTime, LocalDateTime confirmTime, OrderEnum orderStatus,
                 Integer buyNumber) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.wordId = wordId;
        this.commentStatus = commentStatus;
        this.bookPrice = bookPrice;
        this.discount = discount;
        this.expressFare = expressFare;
        this.kickback = kickback;
        this.orderSignTime = orderSignTime;
        this.buyTime = buyTime;
        this.backTime = backTime;
        this.confirmTime = confirmTime;
        this.orderStatus = orderStatus;
        this.buyNumber = buyNumber;
    }
}
