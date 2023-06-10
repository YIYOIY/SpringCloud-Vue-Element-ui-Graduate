package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Admin {
    @TableId("admin_id")
    private Integer adminId;
    private String adminName;
    private String adminPassword;
    public  Admin(){

    }
}
