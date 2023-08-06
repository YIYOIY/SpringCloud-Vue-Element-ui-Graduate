package com.yoi.enumvalue;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author 游弋
 * @create 2023-08-05 5:38
 */

@Getter
public enum CommentEnum {
    COMMENTEDFORBIDDEN(-1, "禁止评价"),
    NOTCOMMENT(0, "未评价"),
    COMMENTED(1, "已评价");
    @EnumValue
    private final Integer code;
    @JsonValue
    private final String comment;

    CommentEnum(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}
