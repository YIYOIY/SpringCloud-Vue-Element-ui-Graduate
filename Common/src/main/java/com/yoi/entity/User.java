package com.yoi.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class User {

    @TableId
    @NotNull(message = "id不能为空")
    private Long id;
    private Long imageId;
    private String userName;
    private String userPassword;
    private SexEnum userSex;
    private LocalDate userBirth;
    private LocalDateTime userSignTime;
    private String userAddress;
    private String userPhone;
    private Double userMoney;

    @TableField(exist = false)
    private Image image;

    @Version
    private LocalDateTime version;

    @TableLogic(value = "0", delval = "null")
    private Integer flag;
}
