package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Admin;
import com.mapper.AdminMapper;
import com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class AdminImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public List<Admin> getAll(String keyword) {
        return adminMapper.getAll(keyword);
    }

    @Override
    public Admin getByAdminId(Integer adminId) {
        return adminMapper.getById(adminId);
    }

    @Override
    public Admin getByLogin(Admin admin) {
        return adminMapper.getByLogin(admin);
    }

    @Override
    public boolean addAdmin(Admin admin) {
        return adminMapper.addAdmin(admin);
    }

    @Override
    public boolean deleteAdmin(Integer adminId) {
        return adminMapper.deleteAdmin(adminId);
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }
}
