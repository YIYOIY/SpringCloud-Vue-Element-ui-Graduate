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

/**
 * @author 游弋
 * @create 2023-08-05 5:27
 */
@Validated
@ToString
@Data
@NoArgsConstructor
public class Shopkeeper {
        @NotNull(message = "id不能为空")
        @TableId
        private Long id;
        private Long imageId;
        private String shopkeeperName;
        private String shopkeeperNickname;
        private String shopkeeperPassword;
        private SexEnum shopkeeperSex;
        private LocalDate shopkeeperBirth;
        private LocalDateTime shopkeeperSignTime;
        private String shopkeeperAddress;
        private String shopkeeperPhone;
        private Double shopkeeperMoney;
        @Version
        private LocalDateTime version;
        @TableLogic(value = "0",delval = "null")
        private Integer flag;
        @TableField(exist = false)
        private Image image;

        public Shopkeeper(Long id, Long imageId, String shopkeeperName, String shopkeeperNickname, String shopkeeperPassword, SexEnum shopkeeperSex, LocalDate shopkeeperBirth, LocalDateTime shopkeeperSignTime, String shopkeeperAddress, String shopkeeperPhone,
                          Double shopkeeperMoney) {
                this.id = id;
                this.imageId = imageId;
                this.shopkeeperName = shopkeeperName;
                this.shopkeeperNickname = shopkeeperNickname;
                this.shopkeeperPassword = shopkeeperPassword;
                this.shopkeeperSex = shopkeeperSex;
                this.shopkeeperBirth = shopkeeperBirth;
                this.shopkeeperSignTime = shopkeeperSignTime;
                this.shopkeeperAddress = shopkeeperAddress;
                this.shopkeeperPhone = shopkeeperPhone;
                this.shopkeeperMoney = shopkeeperMoney;
        }
}
