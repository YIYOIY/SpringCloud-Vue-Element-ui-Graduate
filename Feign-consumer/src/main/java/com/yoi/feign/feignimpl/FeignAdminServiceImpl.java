package com.yoi.feign.feignimpl;

import com.yoi.entity.Admin;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.feign.FeignAdminService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignAdminServiceImpl implements FeignAdminService {
    @Override
    public ReturnInfo index(HttpServletRequest req, String searchName, String oper) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo addAdmin(Admin admin) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo deleteAdmin(Integer adminId) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo alterAdmin(Integer adminId) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo updateAdmin(Admin admin) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }


    @Override
    public ReturnInfo adminLogin(Admin admin) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo userLogin(User user) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo userLogin(String token) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }
}
