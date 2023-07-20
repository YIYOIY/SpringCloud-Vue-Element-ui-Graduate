package com.yoi.feign;

import com.yoi.entity.User;
import com.yoi.feign.feignimpl.FeignUserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@FeignClient(value = "user",fallback = FeignUserServiceImpl.class)
public interface FeignUserService {
    @GetMapping("/users")
    public List<User> index( @RequestParam(value = "req",required = false)HttpServletRequest req,@RequestParam(value = "searchName",required = false) String searchName,@RequestParam(value = "oper",required = false) String oper);
    @GetMapping("/user")
    public User userSelf(@RequestParam(value = "userId",required = false)Integer userId);
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user);
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "userId",required = false)Integer userId);
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody User user);
}
