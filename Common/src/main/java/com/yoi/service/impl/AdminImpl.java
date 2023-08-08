package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Admin;
import com.yoi.entity.Image;
import com.yoi.mapper.AdminMapper;
import com.yoi.mapper.ImageMapper;
import com.yoi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class AdminImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private AdminMapper adminMapper;
    private ImageMapper imageMapper;

    //    setter注入
    @Autowired
    public void setAdminMapper(AdminMapper adminMapper, ImageMapper imageMapper) {
        this.adminMapper = adminMapper;
        this.imageMapper = imageMapper;
    }


    @Override
    public Admin getByAdminId(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
//        根据id取出照片并放入user的image对象中
        Image image = imageMapper.selectById(admin.getImageId());
        if (image != null) {
            admin.setImage(image);
        }
        return admin;
    }

    @Override
    public Admin Login(Admin admin) {
//        虽然是手机号和账户名都可以登录，但前端传递过来是用账户名的字段，所以只要账户名匹配任何一个都可以登录就行了
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("admin_name", admin.getAdminName()).eq("admin_password", admin.getAdminPassword()));
    }


    @Override
    public Page<Admin> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<Admin> Page = new Page<>(pageNo, pageSize);
        Page<Admin> admins = adminMapper.selectPage(Page, new QueryWrapper<Admin>().like("admin_name", keyword));
        for (Admin record : admins.getRecords()) {
            Image image = imageMapper.selectById(record.getImageId());
            if (image != null) {
                record.setImage(image);
            }
        }
        return admins;
    }


    @Override
    public Boolean addAdmin(Admin admin) {
        if (admin.getImage().getPicture() != null && !admin.getImage().getPicture().isEmpty()) {
            String picture = admin.getImage().getPicture();
            Image image = new Image(null, picture, null, null, null);
            if (imageMapper.insert(image) < 0) {
                return false;
            } else {
                admin.setImageId(image.getId());
            }
        }
        return adminMapper.insert(admin) > 0;
    }

    @Override
    public Boolean deleteAdmin(Admin admin) {
        imageMapper.deleteById(admin.getImageId());
        return adminMapper.deleteById(admin.getId()) > 0;
    }

    @Override
    public Boolean updateAdmin(Admin admin) {
        if (admin.getImage().getPicture() != null && !admin.getImage().getPicture().isEmpty()) {
//            如果用户创建时没有上传头像，更新时检测到上传就创建一个图像
            if (admin.getImage().getId() == null) {
                Image image = new Image(null, admin.getImage().getPicture(), null, null, null);
                if (imageMapper.insert(image) > 0) {
                    admin.setImageId(image.getId());
                }else {
                    return false;
                }
            }else {
//                用户创建时已经上传头像，更新时只需要更新头像
                Image image = new Image(admin.getImageId(), admin.getImage().getPicture(), null, null, null);
                if (imageMapper.updateById(image) < 0) {
                    return false;
                }
            }
        }
        return adminMapper.updateById(admin) > 0;
    }
}
