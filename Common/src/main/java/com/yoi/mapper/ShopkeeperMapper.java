package com.yoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoi.entity.Shopkeeper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 游弋
 * @create 2023-08-05 6:11
 */
@Mapper
@Repository
public interface ShopkeeperMapper extends BaseMapper<Shopkeeper> {
}
