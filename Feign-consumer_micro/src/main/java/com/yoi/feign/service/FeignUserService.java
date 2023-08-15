package com.yoi.feign.service;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.feign.service.feignimpl.FeignUserServiceImpl;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Validated
@Component
@FeignClient(value = "user",fallback = FeignUserServiceImpl.class)
public interface FeignUserService {


    @GetMapping("/user/{adminId}/{pageNo}/{pageSize}/{searchName}")
     ReturnInfo<PagePackage<User>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                               @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                               @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                               @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName);
    @GetMapping("/user/{userId}")
     ReturnInfo<User> userSelf(@NotNull @PathVariable("userId") Long userId) ;
    @PostMapping("/add_user")
     ReturnInfo<String> addUser(@RequestBody User user) ;
    @DeleteMapping("/user")
     ReturnInfo<User> deleteUser(@Valid @RequestBody User user) ;
    @PutMapping("/user")
     ReturnInfo<User> updateUser(@Valid @RequestBody User user) ;
    @PutMapping("/user_money")
    ReturnInfo<User> updateUserMoney(@Valid @RequestBody User user) ;
}
