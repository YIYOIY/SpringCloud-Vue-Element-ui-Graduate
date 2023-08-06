package com.yoi.entity;


import com.baomidou.mybatisplus.annotation.TableField;
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
public class Admin {
    @NotNull(message = "id不能为空")
    private Long id;
    private Long imageId;

    private String adminName;
    private String adminNickname;
    private String adminPassword;
    private SexEnum adminSex;
    private LocalDate adminBirth;
    private LocalDateTime adminSignTime;
    private String adminAddress;
    private String adminPhone;
    private Double adminMoney;

    @Version
    private LocalDateTime version;
    @TableLogic(value = "0", delval = "null")
    private Integer flag;

    @TableField(exist = false)
    private Image image;

    public Admin(Long id, Long imageId, String adminName, String adminNickname,
                 String adminPassword, SexEnum adminSex, LocalDate adminBirth,
                 LocalDateTime adminSignTime, String adminAddress, String adminPhone,
                 Double adminMoney) {
        this.id = id;
        this.imageId = imageId;
        this.adminName = adminName;
        this.adminNickname = adminNickname;
        this.adminPassword = adminPassword;
        this.adminSex = adminSex;
        this.adminBirth = adminBirth;
        this.adminSignTime = adminSignTime;
        this.adminAddress = adminAddress;
        this.adminPhone = adminPhone;
        this.adminMoney = adminMoney;
    }
}
