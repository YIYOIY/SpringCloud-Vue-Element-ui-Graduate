package com.yoi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Admin;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface AdminService extends IService<Admin> {
    Admin Login(Admin admin);
    Page<Admin> getAll(String keyword, Integer pageNo, Integer pageSize);

    Boolean addAdmin(Admin admin);
    Boolean deleteAdmin(Long adminId);
    Admin getByAdminId(Long adminId);
    Boolean updateAdmin(Admin admin);

}
