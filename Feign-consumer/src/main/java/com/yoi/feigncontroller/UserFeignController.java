package com.yoi.feigncontroller;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.feign.service.FeignUserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@Validated
@RestController
@RefreshScope
public class UserFeignController {
    @Resource
    private FeignUserService feignUserService;

    @GetMapping("/user/{adminId}/{pageNo}/{pageSize}/{searchName}")
    public ReturnInfo<PagePackage<User>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                               @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                               @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                               @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName) {
        return feignUserService.index(adminId, pageNo,pageSize,searchName);
    }
    @GetMapping("/user/{userId}")
    public ReturnInfo<User> userSelf(@NotNull @PathVariable("userId") Long userId) {
        return feignUserService.userSelf(userId);
    }
    @PostMapping("/add_user")
    public ReturnInfo<String> addUser(@RequestBody User user) {
        return feignUserService.addUser(user);
    }
    @DeleteMapping("/user")
    public ReturnInfo<User> deleteUser(@Valid @RequestBody User user) {
        return feignUserService.deleteUser(user);
    }
    @PutMapping("/user")
    public ReturnInfo<User> updateUser(@Valid @RequestBody User user) {
        return feignUserService.updateUser(user);
    }
}
