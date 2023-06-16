package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
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
        User user = userMapper.selectById(UserId);
        return user;
    }

    @Override
    public User getByLogin(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = userQueryWrapper.eq("user_name", user.getUserName()).eq("user_password", user.getUserPassword());
        User user1 = userMapper.selectOne(eq);
        System.out.println(user1);
        return user1;
    }

//    @Override
//    public Long checkBagData(Integer UserId) {
//        return userMapper.checkBagData(UserId);
//    }

    @Override
    public List<User> getAll(String keyword) {
        List<User> users = userMapper.selectList(null);
        System.out.println(users.toString());
        return users;
    }

    @Override
    public boolean addUser(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = userQueryWrapper.eq("user_name", user.getUserName()).eq("user_password", user.getUserPassword());
        User user1 = userMapper.selectOne(eq);

        if (user1!=null){
            return false;
        }
        return userMapper.insert(user) > 0 ? true : false;
    }

    @Override
    public boolean deleteUser(Integer userId) {
//        int userId = user.getUserId();
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq(user.getUserId() > 0, "userId", userId);
//        userMapper.delete(userQueryWrapper);
        return userMapper.deleteById(userId)>0;
    }

    @Override
    public boolean updateUser(User user) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("男");
        objects.add("女");
        objects.add("保密");
        boolean b = objects.stream().anyMatch(sex -> sex.equals(user.getUserSex()));

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> check = userQueryWrapper.eq("user_name", user.getUserName()).eq("user_password", user.getUserPassword());
        User user1 = userMapper.selectOne(check);
        if (user1!=null){
            return false;
        }

        UpdateWrapper < User > userUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<User> eq = userUpdateWrapper.set(user.getUserName() != null, "user_name", user.getUserName())
                .set(user.getUserPassword() != null, "user_password", user.getUserPassword())
                .set(user.getUserSex() != null , "user_sex", user.getUserSex())
                .set(user.getUserAddress() != null, "user_address", user.getUserAddress())
                .set(user.getUserPhone() != null, "user_phone", user.getUserPhone())
                .set(user.getUserBirth()!=null, "user_birth", user.getUserBirth())
                .eq("user_id",user.getUserId());
        return  userMapper.update(null,eq)>0;
    }
}
