package com.yoi.entity;

import org.apache.poi.ss.formula.functions.Code;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author 游弋
 * @create 2023-07-19 20:38
 */
public class CommonResult {
    private Integer code;
    private String message;
    private T data;
    private CommonResult(Integer code,String message){
        this.message=message;
        this.code=code;
    }
}
