package com.yoi.feign.feignimpl;

import com.yoi.entity.User;
import com.yoi.feign.FeignUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignUserServiceImpl implements FeignUserService {
    @Override
    public List<User> index(HttpServletRequest req, @RequestParam(value = "searchName",required = false) String searchName,@RequestParam(value = "oper",required = false) String oper) {
        return null;
    }

    @Override
    public User userSelf(Integer userId) {
        return null;
    }

    @Override
    public ResponseEntity<String> addUser(User user) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer userId) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> updateUser(User user) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }
}
