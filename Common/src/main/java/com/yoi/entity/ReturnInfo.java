package com.yoi.entity;

import com.yoi.enumvalue.ReturnEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 游弋
 * @create 2023-07-27 15:09
 */
@Data
@NoArgsConstructor
@ToString
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

    public static <T> ReturnInfo<T> withEnumData(ReturnEnum returnEnum, T data){
        return new ReturnInfo<T>(returnEnum.getCode(),returnEnum.getMessage(),data);
    }

    public static <T> ReturnInfo<T> withEnumNoData(ReturnEnum returnEnum){
        return new ReturnInfo<T>(returnEnum.getCode(),returnEnum.getMessage());
    }
}
