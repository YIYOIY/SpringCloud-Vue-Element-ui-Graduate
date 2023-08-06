package com.yoi.enumvalue;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author 游弋
 * @create 2023-08-05 2:08
 */
@Getter
public enum SexEnum {
    UNKNOW(0, "保密"),
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private final Integer sex;

    @JsonValue
    private final String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
