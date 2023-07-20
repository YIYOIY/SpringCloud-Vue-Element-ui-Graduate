package com.yoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoi.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.websocket.server.PathParam;

@Mapper
public interface UserMapper extends BaseMapper<User> {
      User getById(@PathParam("userId") Integer userId);
}
