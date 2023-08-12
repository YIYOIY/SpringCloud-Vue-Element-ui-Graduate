package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Admin;
import com.yoi.entity.Image;
import com.yoi.entity.Order;
import com.yoi.entity.User;
import com.yoi.mapper.AdminMapper;
import com.yoi.mapper.ImageMapper;
import com.yoi.mapper.OrderMapper;
import com.yoi.mapper.UserMapper;
import com.yoi.service.OrderService;
import com.yoi.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Random;

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
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderService orderService;
    @Resource
    AdminMapper adminMapper;

    @Override
    public User getById(Long UserId) {
        User user = userMapper.selectById(UserId);
//        根据id取出照片并放入user的image对象中
        if (!ObjectUtils.isEmpty(imageMapper.selectById(user.getImageId()))) {
            user.setImage(imageMapper.selectById(user.getImageId()));
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
            if (!ObjectUtils.isEmpty(image)) {
                record.setImage(image);
            }
        }
        return users;

    }

    @Override
    public boolean addUser(User user) {
        if (!ObjectUtils.isEmpty(user.getImage())) {
            String picture = user.getImage().getPicture();
//            用户没有传递图片时，前端会自动传递一个图片对象， 对象中的图片地址是“”，会导致数据库默认的404图片被“”取代
            if ("".equals(picture)) {
                picture = null;
            }
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
        User us = userMapper.selectById(user.getId());
        QueryWrapper<Order> orderByUserId = new QueryWrapper<Order>().eq("user_id", us.getId());
        for (Order order : orderMapper.selectList(orderByUserId)) {
            orderService.deleteOrder(order);
        }

//      用户注销后将用户资金平分给管理员
        if (adminMapper.selectCount(null)>0){
            for (Admin admin : adminMapper.selectList(null)) {
                admin.setAdminMoney(admin.getAdminMoney() + us.getUserMoney() / adminMapper.selectCount(null));
                adminMapper.updateById(admin);
            }
        }

        imageMapper.deleteById(userMapper.selectById(user.getId()).getImageId());
        return userMapper.deleteById(user.getId()) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        if (!ObjectUtils.isEmpty(user.getImage())) {
//            如果用户创建时没有上传头像，更新时检测到上传就创建一个图像
            if (user.getImage().getId() == null) {
                Image image = new Image(null, user.getImage().getPicture(), null, null, null);
                if (imageMapper.insert(image) > 0) {
                    user.setImageId(image.getId());
                } else {
                    return false;
                }
            } else {
//                用户创建时已经上传头像，更新时只需要更新头像
                Image image = new Image(user.getImageId(), user.getImage().getPicture(), null, null, null);
                if (imageMapper.updateById(image) < 0) {
                    return false;
                }
            }
        }
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean updateUserMoney(User user) {
        Random random = new Random();
        double v = random.nextDouble() * 100000000;
        user.setUserMoney(user.getUserMoney()+v);
        return userMapper.updateById(user)>0;
    }
}
