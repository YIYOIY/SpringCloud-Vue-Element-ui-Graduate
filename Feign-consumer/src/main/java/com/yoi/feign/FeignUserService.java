package com.yoi.feign;

import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.feign.feignimpl.FeignUserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@FeignClient(value = "user",fallback = FeignUserServiceImpl.class)
public interface FeignUserService {
    @GetMapping("/users")
    public ReturnInfo index(@RequestParam(value = "req",required = false)HttpServletRequest req, @RequestParam(value = "searchName",required = false) String searchName, @RequestParam(value = "oper",required = false) String oper);
    @GetMapping("/user")
    public ReturnInfo userSelf(@RequestParam(value = "userId",required = false)Integer userId);
    @PostMapping("/addUser")
    public ReturnInfo addUser(@RequestBody User user);
    @DeleteMapping("/user")
    public ReturnInfo deleteUser(@RequestParam(value = "userId",required = false)Integer userId);
    @PutMapping("/user")
    public ReturnInfo updateUser(@RequestBody User user);
}
