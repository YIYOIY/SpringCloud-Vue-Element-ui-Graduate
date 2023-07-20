package com.yoi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.User;


import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface UserService extends IService<User> {
    User getByLogin(User user);
//    Long checkBagData(Integer UserId);


    User getById(Integer UserId);

    List<User> getAll(String keyword);

    boolean addUser(User user);

    boolean deleteUser(Integer userId);

    boolean updateUser(User user);

}
