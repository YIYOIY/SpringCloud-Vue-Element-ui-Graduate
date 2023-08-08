package com.yoi.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.yoi.enumvalue.SexEnum;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Admin {
    @NotNull(message = "id不能为空")
    private Long id;
    private Long imageId;

    private String adminName;
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
}
