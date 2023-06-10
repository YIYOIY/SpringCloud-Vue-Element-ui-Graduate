package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.sql.Date;
@ToString
@Data
@AllArgsConstructor
public class User {
    @TableId("user_id")
    private Integer userId;
    private String userName;
    private String userPassword;
    private String userSex;
    private String userAddress;
    private String userPhone;
    private Date userBirth;
    private User(){
    }
}
