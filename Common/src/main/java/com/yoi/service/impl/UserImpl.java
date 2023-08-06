package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.User;
import com.yoi.mapper.UserMapper;
import com.yoi.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public User getById(Long UserId) {
        return  userMapper.selectById(UserId);
    }

    @Override
    public User Login(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = userQueryWrapper.eq("user_name", user.getUserName()).eq("user_password", user.getUserPassword());
        User user1 = userMapper.selectOne(eq);
        System.out.println(user1);
        return user1;
    }


    @Override
    public Page<User> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<User> page = new Page<>(pageNo, pageSize);
        return userMapper.selectPage(page,new QueryWrapper<User>().like("shopkeeper_name",keyword));
    }

    @Override
    public boolean addUser(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = userQueryWrapper.eq("user_name", user.getUserName());
        User user1 = userMapper.selectOne(eq);

        if (user1!=null){
            return false;
        }
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean deleteUser(Long userId) {
        return userMapper.deleteById(userId)>0;
    }

    @Override
    public boolean updateUser(User user) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("男");
        objects.add("女");
        objects.add("保密");
        boolean b = objects.stream().anyMatch(sex -> sex.equals(user.getUserSex()));
        if(!b){
            return false;
        }


        UpdateWrapper < User > userUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<User> eq = userUpdateWrapper
                .set(user.getUserPassword() != null, "user_password", user.getUserPassword())
                .set(user.getUserSex() != null , "user_sex", user.getUserSex())
                .set(user.getUserAddress() != null, "user_address", user.getUserAddress())
                .set(user.getUserPhone() != null, "user_phone", user.getUserPhone())
                .set(user.getUserBirth()!=null, "user_birth", user.getUserBirth())
                .eq("user_id",user.getId());
        return  userMapper.update(null,eq)>0;
    }
}
