package com.yoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-08-06 6:51
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PagePackage<T> {
    private List<T> data;
    private Long current;
    private Long pages;
    private Long pageSize;
    private Long total;
    private boolean hasNext;
    private boolean hasPrevious;
}
