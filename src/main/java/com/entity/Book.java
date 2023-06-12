package com.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Date;
@ToString
@AllArgsConstructor
@Data
public class Book {
    @TableId("book_id")
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private String bookInfo;
    private String bookDetail;
    @TableField(select = false)
    private String seriesName;
    private Double bookPrice;
    private Double bookNum;
    private String bookPicture;
    private String bookFactory;
    @TableField("book_addDate")
    private Date bookAddDate;
    public Book() {
    }
}
