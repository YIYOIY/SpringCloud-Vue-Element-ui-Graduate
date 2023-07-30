package com.yoi.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoi.config.JwtUtils;
import com.yoi.entity.Admin;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.exception.SelfExcept;
import com.yoi.service.AdminService;
import com.yoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/login")
@RestController
public class CheckLogin {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private final static ObjectMapper om = new ObjectMapper();

    @PostMapping("/admin")
    public ReturnInfo adminLogin(@RequestBody Admin admin) {
        try {
            Admin loginAdmin = adminService.getByLogin(admin);
            if (loginAdmin != null) {

                //controller层接收数据，生成token，并响应
                Map<String, String> payload = new HashMap<>();
                payload.put("id", loginAdmin.getAdminId().toString());
                payload.put("name", loginAdmin.getAdminName());
                //生成JWT令牌
                String token = JwtUtils.getToken(payload);
                System.out.println(token);
                return new ReturnInfo(200, "登录认证成功！", loginAdmin, token);
            } else {
                return new ReturnInfo(404, "管理员登录失败，不存在此管理员！");
            }
        } catch (Exception e) {
            throw new SelfExcept("CheckLogin的admin出现的问题\t" + e);
        }
    }

    @PostMapping("/user")
    public ReturnInfo userLogin(@RequestBody User user) {
        try {
            User loginUser = userService.getByLogin(user);
            if (loginUser != null) {
                //controller层接收数据，生成token，并响应
                Map<String, String> payload = new HashMap<>();
                payload.put("id", loginUser.getUserId().toString());
                payload.put("name", loginUser.getUserName());
                //生成JWT令牌
                String token = JwtUtils.getToken(payload);
                return new ReturnInfo(200, "登录成功！", loginUser, token);
            } else {
                return new ReturnInfo(200, "用户不存在，请注册后再登录！");
            }
        } catch (Exception e) {
            throw new SelfExcept("CheckLogin的user出现的问题\t" + e);
        }
    }

    @GetMapping("/checkToken")
    public ReturnInfo userLogin(@RequestParam("token")String token) {
        try {
            System.out.println(token);//输出令牌
            JwtUtils.verify(token);//验证令牌
            return new ReturnInfo(200, "token验证成功！");
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            return new ReturnInfo(404, "无效签名！");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            return new ReturnInfo(401, "token过期！");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            return new ReturnInfo(401, "token算法不一致！");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(500, "token失效！");
        }
    }
}
