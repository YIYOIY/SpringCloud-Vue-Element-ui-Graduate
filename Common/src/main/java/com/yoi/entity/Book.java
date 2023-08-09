package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Validated
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @NotNull(message = "id不能为空")
    @TableId
    private Long id;
    private Long seriesId;
    private Long shopkeeperId;
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
    @TableLogic(value = "null", delval = "now()")
    private LocalDateTime flag;
    @TableField(exist = false)
    private Shopkeeper shopkeeper;
    @TableField(exist = false)
    private Series series;
    @TableField(exist = false)
    private Image image;
    @TableField(exist = false)
    private Word word;
}
