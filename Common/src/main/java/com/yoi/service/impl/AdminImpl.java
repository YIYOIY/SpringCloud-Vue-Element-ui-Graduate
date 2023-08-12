package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Admin;
import com.yoi.entity.Image;
import com.yoi.entity.Shopkeeper;
import com.yoi.entity.User;
import com.yoi.mapper.AdminMapper;
import com.yoi.mapper.ImageMapper;
import com.yoi.mapper.ShopkeeperMapper;
import com.yoi.mapper.UserMapper;
import com.yoi.service.AdminService;
import com.yoi.service.ShopkeeperService;
import com.yoi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
@RequiredArgsConstructor
public class AdminImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private final AdminMapper adminMapper;
    private final ImageMapper imageMapper;
    private final ShopkeeperMapper shopkeeperMapper;
    private final UserMapper userMapper;
    private final ShopkeeperService shopkeeperService;
    private final UserService userService;


    @Override
    public Admin getByAdminId(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
//        根据id取出照片并放入user的image对象中
        Image image = imageMapper.selectById(admin.getImageId());
        if (!ObjectUtils.isEmpty(image)) {
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
            if (!ObjectUtils.isEmpty(image)) {
                record.setImage(image);
            }
        }
        return admins;
    }


    @Override
    public Boolean addAdmin(Admin admin) {
        if (!ObjectUtils.isEmpty(admin.getImage())) {
            String picture = admin.getImage().getPicture();
            //            用户没有传递图片时，前端会自动传递一个图片对象， 对象中的图片地址是“”，会导致数据库默认的404图片被“”取代
            if ("".equals(picture)) {
                picture = null;
            }
            Image image = new Image(null, picture, null, null, null);
            if (imageMapper.insert(image) < 0) {
                return false;
            } else {
                admin.setImageId(image.getId());
            }
        }
        int insert = adminMapper.insert(admin);
//        自动赋予管理员商家和用户身份
        User user = new User();
        user.setId(admin.getId());
        user.setUserName("admin" + admin.getAdminName());
        user.setUserPassword(admin.getAdminPassword());
        userMapper.insert(user);

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(admin.getId());
        shopkeeper.setShopkeeperName("admin" + admin.getAdminName());
        shopkeeper.setShopkeeperPassword(admin.getAdminPassword());
        shopkeeperMapper.insert(shopkeeper);
        return insert > 0;
    }

    @Override
    public Boolean deleteAdmin(Admin admin) {
        Admin am = adminMapper.selectById(admin.getId());
//        将管理员的其他身份一同删除,因为可能已经被单独删除了，所以不能进行删除成功判断
        User user = new User();
        user.setId(am.getId());
        userService.deleteUser(user);
        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(am.getId());
        shopkeeperService.deleteShopkeeper(shopkeeper);
//      企业注销后将企业资金平分给管理员
        if (adminMapper.selectCount(new QueryWrapper<Admin>().ne("id",am.getId())) > 0) {
            for (Admin ad : adminMapper.selectList(new QueryWrapper<Admin>().ne("id",am.getId()))) {
                ad.setAdminMoney(ad.getAdminMoney() + am.getAdminMoney() / adminMapper.selectCount(new QueryWrapper<Admin>().ne("id",am.getId())));
                adminMapper.updateById(ad);
            }
        }
        imageMapper.deleteById(adminMapper.selectById(am.getId()).getImageId());
        return adminMapper.deleteById(am.getId()) > 0;
    }

    @Override
    public Boolean updateAdmin(Admin admin) {
        if (!ObjectUtils.isEmpty(admin.getImage())) {
//            如果用户创建时没有上传头像，更新时检测到上传就创建一个图像
            if (admin.getImage().getId() == null) {
                Image image = new Image(null, admin.getImage().getPicture(), null, null, null);
                if (imageMapper.insert(image) > 0) {
                    admin.setImageId(image.getId());
                } else {
                    return false;
                }
            } else {
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
