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
@Validated
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Series {
    @NotNull(message = "id不能为空")
    @TableId
    private Long id;
    private String seriesName;
    private LocalDateTime seriesSignTime;

    @Version
    private LocalDateTime version;
    @TableLogic(value = "0",delval = "null")
    private Integer flag;
}
