package com.yoi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Admin;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface AdminService extends IService<Admin> {
    List<Admin> getAll(String keyword);
    Admin getByAdminId(Integer adminId);
    Admin getByLogin(Admin admin);
    boolean addAdmin(Admin admin);
    boolean deleteAdmin(Integer adminId);
    boolean updateAdmin(Admin admin);

}
