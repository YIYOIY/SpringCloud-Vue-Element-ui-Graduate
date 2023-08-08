package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.yoi.enumvalue.CommentEnum;
import com.yoi.enumvalue.OrderEnum;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

}
