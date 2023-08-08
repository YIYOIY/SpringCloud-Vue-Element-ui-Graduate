package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Image;
import com.yoi.entity.User;
import com.yoi.mapper.ImageMapper;
import com.yoi.mapper.UserMapper;
import com.yoi.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    ImageMapper imageMapper;

    @Override
    public User getById(Long UserId) {
        User user = userMapper.selectById(UserId);
//        根据id取出照片并放入user的image对象中
        Image image = imageMapper.selectById(user.getImageId());
        if (image != null) {
            user.setImage(image);
        }
        return user;
    }


    @Override
    public User Login(User user) {
        //        虽然是手机号和账户名都可以登录，但前端传递过来是用账户名的字段，所以只要账户名匹配任何一个都可以登录就行了
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = userQueryWrapper.eq("user_name", user.getUserName()).eq("user_password", user.getUserPassword());
        return userMapper.selectOne(eq);
    }

    @Override
    public Page<User> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<User> page = new Page<>(pageNo, pageSize);

        Page<User> users = userMapper.selectPage(page, new QueryWrapper<User>().like("user_name", keyword));
        for (User record : users.getRecords()) {
            Image image = imageMapper.selectById(record.getImageId());
            if (image != null) {
                record.setImage(image);
            }
        }
        return users;

    }

    @Override
    public boolean addUser(User user) {
        if (user.getImage().getPicture() != null && !user.getImage().getPicture().isEmpty()) {
            String picture = user.getImage().getPicture();
            Image image = new Image(null, picture, null, null, null);
            if (imageMapper.insert(image) < 0) {
                return false;
            } else {
                user.setImageId(image.getId());
            }
        }
        return userMapper.insert(user) > 0;

    }

    @Override
    public boolean deleteUser(User user) {
        imageMapper.deleteById(user.getImageId());
        return userMapper.deleteById(user.getId()) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        if (user.getImage().getPicture() != null && !user.getImage().getPicture().isEmpty()) {
//            如果用户创建时没有上传头像，更新时检测到上传就创建一个图像
            if (user.getImage().getId() == null) {
                Image image = new Image(null, user.getImage().getPicture(), null, null, null);
                if (imageMapper.insert(image) > 0) {
                    user.setImageId(image.getId());
                }else {
                    return false;
                }
            }else {
//                用户创建时已经上传头像，更新时只需要更新头像
                Image image = new Image(user.getImageId(), user.getImage().getPicture(), null, null, null);
                if (imageMapper.updateById(image) < 0) {
                    return false;
                }
            }
        }

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .set(user.getUserName() != null, "user_name", user.getUserName())
                .set(user.getUserPassword() != null, "user_password", user.getUserPassword())
                .set(user.getImageId()!=null,"image_id",user.getImageId())
                .set(user.getUserPhone() != null, "user_phone", user.getUserPhone())
                .set(user.getUserBirth() != null, "user_birth", user.getUserBirth())
                .set(user.getUserSex() != null, "user_sex", user.getUserSex())
                .set(user.getUserAddress() != null, "user_address", user.getUserAddress())
                .eq("id", user.getId());
        return userMapper.update(null, userUpdateWrapper) > 0;
    }
}
