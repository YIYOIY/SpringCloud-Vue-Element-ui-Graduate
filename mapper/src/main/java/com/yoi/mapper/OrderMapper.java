package com.yoi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoi.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
