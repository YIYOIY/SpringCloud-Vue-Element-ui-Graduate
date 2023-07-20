package com.yoi.feign.feignimpl;

import com.yoi.entity.Admin;
import com.yoi.entity.User;
import com.yoi.feign.FeignAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignAdminServiceImpl implements FeignAdminService {
    @Override
    public List<Admin> index(HttpServletRequest req, String searchName, String oper) {
        return null;
    }

    @Override
    public ResponseEntity<String> addAdmin(Admin admin) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> deleteAdmin(Integer adminId) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public Admin alterAdmin(Integer adminId) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateAdmin(Admin admin) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }


    @Override
    public Admin adminLogin(Admin admin) {
        return null;
    }

    @Override
    public User userLogin(User user) {
        return null;
    }
}
