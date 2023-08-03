package com.yoi.entity;

/**
 * @author 游弋
 * @create 2023-07-27 15:09
 */
public class ReturnInfo<T> {
    private Integer code;
    private String message;
    private String token;
    private T data;

    public ReturnInfo(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public ReturnInfo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ReturnInfo(Integer code, String message,T data,String token) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public ReturnInfo() {
    }

    public static <T> ReturnInfo<T> withEnumData(ReturnEnum returnEnum, T data){
        return new ReturnInfo(returnEnum.getCode(),returnEnum.getMessage(),data);
    }

    public static ReturnInfo withEnumNoData(ReturnEnum returnEnum){
        return new ReturnInfo(returnEnum.getCode(),returnEnum.getMessage());
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
