package com.yoi.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.yoi.config.JwtUtils;
import com.yoi.entity.Admin;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Shopkeeper;
import com.yoi.entity.User;
import com.yoi.service.AdminService;
import com.yoi.service.ShopkeeperService;
import com.yoi.service.UserService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/admin")
    public ReturnInfo<Admin> adminLogin(@RequestBody Admin admin) {
        Admin loginAdmin = adminService.Login(admin);
        if (loginAdmin != null) {
            //controller层接收数据，生成token，并响应
            Map<String, String> payload = new HashMap<>();
            payload.put("id", loginAdmin.getId().toString());
            payload.put("name", loginAdmin.getAdminName());
            payload.put("type", "admin");
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
//            存入redis中，token验证时会验证是否不能使用此token
            stringRedisTemplate.opsForValue().set("admin" + loginAdmin.getId().toString(), token, 5, TimeUnit.MINUTES);
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
            payload.put("type", "user");
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            stringRedisTemplate.opsForValue().set("user" + loginUser.getId().toString(), token, 5, TimeUnit.MINUTES);
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
            payload.put("type", "shopkeeper");
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            stringRedisTemplate.opsForValue().set("shopkeeper" + loginShopkeeper.getId().toString(), token, 5, TimeUnit.MINUTES);
            return new ReturnInfo<>(200, "登录成功！", loginShopkeeper, token);
        } else {
            return new ReturnInfo<>(200, "商户不存在，请注册后再登录！");
        }
    }

    @GetMapping("/checkToken")
    public ReturnInfo<String> checkToken(@RequestParam("token") String token) {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        try {
            JwtUtils.verify(token);
            DecodedJWT ver = JwtUtils.getToken(token);
            String id = ver.getClaim("id").asString();
            String name = ver.getClaim("name").asString();
            String type = ver.getClaim("type").asString();
            String s = stringStringValueOperations.get(type + id);

            if (StringUtil.isNullOrEmpty(s) || !s.equals(token)) {
                throw new Exception("token不存在！");
            }
            //生成新的token，并响应
            Map<String, String> payload = new HashMap<>();
            payload.put("id", id);
            payload.put("name", name);
            payload.put("type", type);
            //生成JWT令牌并重新覆盖redis中的令牌
            String newToken = JwtUtils.getToken(payload);
            stringRedisTemplate.opsForValue().set(type + id, newToken, 5, TimeUnit.MINUTES);

            System.out.println("redis中拿到id为" + id + "名称为" + name + "身份为" + type + "的token\n"
                    + s.substring(60) + "\n前端传递的token\n" + token.substring(60));//输出令牌

            return new ReturnInfo<>(200, "token刷新成功！", newToken);
        } catch (Exception e) {
            log.warn(e.toString());
            return new ReturnInfo<>(404, "token验证失败，该token已无效或不存在！");
        }
    }
}
