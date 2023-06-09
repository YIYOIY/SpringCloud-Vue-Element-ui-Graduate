package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Date;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private String bookInfo;
    private String bookDetail;
    private String seriesName;
    private Double bookPrice;
    private Double bookNum;
    private String bookPicture;
    private String bookFactory;
    private Date bookAddDate;
}
