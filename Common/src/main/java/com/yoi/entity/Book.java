package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@ToString
@Data
@NoArgsConstructor
public class Book {
    @NotNull(message = "id不能为空")
    @TableId
    private Long id;
    private Long seriesId;
    private Long shopperKeeperId;
    private Long imageId;
    private Long wordId;

    private String bookName;
    private String bookAuthor;
    private String bookFactory;
    private LocalDate bookAddDate;
    private Integer bookNumber;
    private Double bookPrice;
    private Double discount;
    private Double expressFare;
    private Double kickback;
    private LocalDateTime bookSignTime;
    @Version
    private LocalDateTime version;
    @TableLogic(value = "null",delval = "now()")
    private LocalDateTime flag;

    @TableField(exist = false)
    private Shopkeeper shopkeeper;
    @TableField(exist = false)
    private Series series;
    @TableField(exist = false)
    private List<Image> image;
    @TableField(exist = false)
    private Word word;

    public Book(Long id, Long seriesId, Long shopperKeeperId, Long imageId, Long wordId,
                String bookName, String bookAuthor, String bookFactory, LocalDate bookAddDate,
                Integer bookNumber, Double bookPrice, Double discount, Double expressFare,
                Double kickback, LocalDateTime bookSignTime) {
        this.id = id;
        this.seriesId = seriesId;
        this.shopperKeeperId = shopperKeeperId;
        this.imageId = imageId;
        this.wordId = wordId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookFactory = bookFactory;
        this.bookAddDate = bookAddDate;
        this.bookNumber = bookNumber;
        this.bookPrice = bookPrice;
        this.discount = discount;
        this.expressFare = expressFare;
        this.kickback = kickback;
        this.bookSignTime = bookSignTime;
    }
}
