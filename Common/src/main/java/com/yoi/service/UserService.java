package com.yoi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.User;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface UserService extends IService<User> {
    User Login(User user);
    User getById(Long UserId);

    Page<User> getAll(String keyword, Integer pageNo, Integer pageSize);

    boolean addUser(User user);

    boolean deleteUser(Long userId);

    boolean updateUser(User user);

}
