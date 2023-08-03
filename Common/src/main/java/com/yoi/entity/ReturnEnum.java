package com.yoi.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author 游弋
 * @create 2023-07-27 15:17
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ReturnEnum {
    ADD_SUCCESS(200,"添加成功!"),
    DELETE_SUCCESS(200,"删除成功!"),
    ALTER_SUCCESS(200,"修改成功!"),
    ADD_FAILED(400,"添加失败!"),
    DELETE_FAILED(400,"删除失败!"),
    ALTER_FAILED(400,"修改失败!"),
    USER_FAILED(400,"用户名已存在！"),
    USER_SUCCESS(200,"用户添加成功!"),
    WAIT(404,"WAITING FOR SOME MINUTES");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
