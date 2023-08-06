package com.yoi.enumvalue;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author 游弋
 * @create 2023-08-05 5:43
 */
@Getter
public enum OrderEnum {
    CONFIRM(2, "已签收"),
    BUY(1, "已购买"),
    NOTPAIED(0, "未支付"),
    BACKING(-1, "已退货");
    @EnumValue
    private final Integer code;
    @JsonValue
    private final String status;

    OrderEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }
}
