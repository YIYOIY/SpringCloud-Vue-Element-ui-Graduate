package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getById(Integer UserId) {
        return userMapper.getById(UserId);
    }

    @Override
    public User getByLogin(User user) {
        return userMapper.getByLogin(user);
    }

    @Override
    public Long checkBagData(Integer UserId) {
        return userMapper.checkBagData(UserId);
    }

    @Override
    public List<User> getAll(String keyword) {
        return userMapper.getAll(keyword);
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public boolean deleteUser(Integer userId) {
//        int userId = user.getUserId();
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq(user.getUserId() > 0, "userId", userId);
//        userMapper.delete(userQueryWrapper);
        return userMapper.deleteUser(userId);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
