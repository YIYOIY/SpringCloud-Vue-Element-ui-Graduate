package com.yoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoi.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin>{
}
