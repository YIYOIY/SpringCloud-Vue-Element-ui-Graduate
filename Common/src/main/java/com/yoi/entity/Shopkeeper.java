package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.yoi.enumvalue.ShopkeeperEnum;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Shopkeeper {
        @TableId
        @NotNull(message = "id不能为空")
        private Long id;
        private Long imageId;
        private String shopkeeperName;
        private String shopkeeperPassword;
        private ShopkeeperEnum shopkeeperQuality;
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
}
