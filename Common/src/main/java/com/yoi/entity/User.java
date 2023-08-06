package com.yoi.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.yoi.enumvalue.SexEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Validated
@ToString
@Data
@NoArgsConstructor
public class User {
    @NotNull(message = "id不能为空")
    @TableId
    private Long id;
    private Long imageId;
    private String userName;
    private String userNickname;
    private String userPassword;
    private SexEnum userSex;
    private LocalDate userBirth;
    private LocalDateTime userSignTime;
    private String userAddress;
    private String userPhone;
    private Double userMoney;
    @Version
    private LocalDateTime version;
    @TableLogic(value = "0",delval = "null")
    private Integer flag;
    @TableField(exist = false)
    private Image image;

    public User(Long id, Long imageId, String userName, String userNickname,
                String userPassword, SexEnum userSex, LocalDate userBirth,
                LocalDateTime userSignTime, String userAddress, String userPhone,
                Double userMoney) {
        this.id = id;
        this.imageId = imageId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userSignTime = userSignTime;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userMoney = userMoney;
    }
}
