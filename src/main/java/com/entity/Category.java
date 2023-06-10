package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Category {
    @TableId("series_id")
    private Integer seriesId;
    private String seriesName;
    public Category() {
    }
}
