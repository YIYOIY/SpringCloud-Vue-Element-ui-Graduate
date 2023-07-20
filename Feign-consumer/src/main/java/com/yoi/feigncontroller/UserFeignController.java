package com.yoi.feigncontroller;

import com.yoi.entity.User;
import com.yoi.feign.FeignUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
public class UserFeignController {
    @Autowired
    private FeignUserService feignUserService;
    @GetMapping("/users")
    public List<User> index(@RequestParam(value = "req",required = false)HttpServletRequest req, @RequestParam(value = "searchName",required = false) String searchName,@RequestParam(value = "oper",required = false) String oper){
        return feignUserService.index(req, searchName, oper);
    }
    @GetMapping("/user")
    public User userSelf(@RequestParam(value = "userId",required = false)Integer userId){
        return feignUserService.userSelf(userId);
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user){
        return feignUserService.addUser(user);
    }
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "userId",required = false)Integer userId){
        return feignUserService.deleteUser(userId);
    }
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody User user){
        return feignUserService.updateUser(user);
    }
}
