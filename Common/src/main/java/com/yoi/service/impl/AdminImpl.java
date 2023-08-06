package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Admin;
import com.yoi.mapper.AdminMapper;
import com.yoi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class AdminImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private AdminMapper adminMapper;
//    setter注入
    @Autowired
    public void setAdminMapper(AdminMapper adminMapper){
        this.adminMapper=adminMapper;
    }
    @Override
    public Page<Admin> getAll(String keyword,Integer pageNo,Integer pageSize) {
        Page<Admin> Page = new Page<>(pageNo, pageSize);
        return adminMapper.selectPage(Page, new QueryWrapper<Admin>().like("admin_name", keyword));
    }

    @Override
    public Admin getByAdminId(Long adminId) {
        return adminMapper.selectById(adminId);
    }

    @Override
    public Admin Login(Admin admin) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("admin_name",admin.getAdminName()).eq("admin_password",admin.getAdminPassword()));
    }

    @Override
    public Boolean addAdmin(Admin admin) {
        try {
            return adminMapper.insert(admin)>0;
        }catch (DataAccessException e){
            System.out.println("抓到他了，唯一键重复");
            return false;
        }
    }

    @Override
    public Boolean deleteAdmin(Long adminId) {
        return adminMapper.deleteById(adminId)>0;
    }

    @Override
    public Boolean updateAdmin(Admin admin) {
        return adminMapper.updateById(admin)>0;
    }
}
