package com.yoi.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yoi.config.JwtUtils;
import com.yoi.entity.Admin;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Shopkeeper;
import com.yoi.entity.User;
import com.yoi.service.AdminService;
import com.yoi.service.ShopkeeperService;
import com.yoi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/login")
@RestController
@RefreshScope
@Validated
@Slf4j
public class CheckLogin {
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;
    @Resource
    private ShopkeeperService shopkeeperService;

    @PostMapping("/admin")
    public ReturnInfo<Admin> adminLogin(@RequestBody Admin admin) {
            Admin loginAdmin = adminService.Login(admin);
            if (loginAdmin != null) {
                //controller层接收数据，生成token，并响应
                Map<String, String> payload = new HashMap<>();
                payload.put("id", loginAdmin.getId().toString());
                payload.put("name", loginAdmin.getAdminName());
                //生成JWT令牌
                String token = JwtUtils.getToken(payload);
                System.out.println(token);
                return new ReturnInfo<>(200, "登录认证成功！", loginAdmin, token);
            } else {
                return new ReturnInfo<>(404, "管理员登录失败，不存在此管理员！");
            }
    }

    @PostMapping("/user")
    public ReturnInfo<User> userLogin(@RequestBody User user) {
            User loginUser = userService.Login(user);
            if (loginUser != null) {
                //controller层接收数据，生成token，并响应
                Map<String, String> payload = new HashMap<>();
                payload.put("id", loginUser.getId().toString());
                payload.put("name", loginUser.getUserName());
                //生成JWT令牌
                String token = JwtUtils.getToken(payload);
                return new ReturnInfo<>(200, "登录成功！", loginUser, token);
            } else {
                return new ReturnInfo<>(200, "用户不存在，请注册后再登录！");
            }
    }

    @PostMapping("/shopkeeper")
    public ReturnInfo<Shopkeeper> shopkeeperLogin(@RequestBody Shopkeeper shopkeeper) {
            Shopkeeper loginShopkeeper = shopkeeperService.Login(shopkeeper);
            if (loginShopkeeper != null) {
                //controller层接收数据，生成token，并响应
                Map<String, String> payload = new HashMap<>();
                payload.put("id", loginShopkeeper.getId().toString());
                payload.put("name", loginShopkeeper.getShopkeeperName());
                //生成JWT令牌
                String token = JwtUtils.getToken(payload);
                return new ReturnInfo<>(200, "登录成功！", loginShopkeeper, token);
            } else {
                return new ReturnInfo<>(200, "商户不存在，请注册后再登录！");
            }
    }

    @GetMapping("/checkToken")
    public ReturnInfo<String> checkToken(@RequestParam("token")String token) {
        try {
            System.out.println(token);//输出令牌
            JwtUtils.verify(token);//验证令牌
            return new ReturnInfo<>(200, "token验证成功！");
        } catch (SignatureVerificationException e) {
            log.warn(e.toString());
            return new ReturnInfo<>(404, "无效签名！");
        } catch (TokenExpiredException e) {
            log.warn(e.toString());
            return new ReturnInfo<>(401, "token过期！");
        } catch (AlgorithmMismatchException e) {
            log.warn(e.toString());
            return new ReturnInfo<>(401, "token算法不一致！");
        } catch (Exception e) {
            log.warn(e.toString());
            return new ReturnInfo<>(500, "token失效！");
        }
    }

}
