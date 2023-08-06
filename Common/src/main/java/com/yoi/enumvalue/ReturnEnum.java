package com.yoi.enumvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 游弋
 * @create 2023-07-27 15:17
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ReturnEnum {
    ADD_SUCCESS(200,"信息添加成功!"),
    ADD_FAILED(400,"添加失败!"),
    NAME_FAILED(400,"账户名已存在！"),

    DELETE_SUCCESS(200,"删除成功!"),
    DELETE_FAILED(400,"删除失败!"),

    ALTER_SUCCESS(200,"修改成功!"),
    ALTER_FAILED(400,"修改失败!"),


    GET_FAILED(400,"获取失败，Id错误"),
    GET_SUCCESS(200,"获取信息成功"),

    WAIT(404,"WAITING FOR SOME MINUTES");

    private Integer code;
    private String message;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
