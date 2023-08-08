package com.yoi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author 游弋
 * @create 2023-08-05 5:55
 */
@Validated
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    @NotNull(message = "id不能为空")
    @TableId
    private Long id;
    private String bookDescribe;
    private String bookDetail;
    private String comments;


    private LocalDateTime wordSignTime;
    @Version
    private LocalDateTime version;
    @TableLogic(value = "null",delval = "now()")
    private LocalDateTime flag;
}
