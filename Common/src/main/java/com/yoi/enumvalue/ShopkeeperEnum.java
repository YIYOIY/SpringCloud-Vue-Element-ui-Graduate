package com.yoi.enumvalue;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author 游弋
 * @create 2023-08-05 2:08
 */
@Getter
public enum ShopkeeperEnum {
    MALE(0, "封闭公司"),
    FEMALE(1, "公众公司");

    @EnumValue
    private final Integer code;

    @JsonValue
    private final String quality;

    ShopkeeperEnum(Integer code, String quality) {
        this.code = code;
        this.quality = quality;
    }
}
