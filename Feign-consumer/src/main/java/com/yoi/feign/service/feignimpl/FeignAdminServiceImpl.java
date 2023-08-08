package com.yoi.feign.service.feignimpl;

import com.yoi.entity.*;
import com.yoi.feign.service.FeignAdminService;
import org.springframework.stereotype.Component;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignAdminServiceImpl implements FeignAdminService {
    @Override
    public ReturnInfo<PagePackage<Admin>> index(Long adminId,
                                                Integer pageNo,
                                                Integer pageSize,
                                                String searchName) {
        return new ReturnInfo<>(404, "后台服务宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Admin> addAdmin(Admin admin) {
        return new ReturnInfo<>(404, "账户名重复，重新输入再试！");
    }

    @Override
    public ReturnInfo<Admin> deleteAdmin(Admin admin) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Admin> alterAdmin(Long adminId) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Admin> updateAdmin(Admin admin) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }


    @Override
    public ReturnInfo<Admin> adminLogin(Admin admin) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<User> userLogin(User user) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<String> checkToken(String token) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Shopkeeper> shopkeeperLogin(Shopkeeper shopkeeper) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

}
