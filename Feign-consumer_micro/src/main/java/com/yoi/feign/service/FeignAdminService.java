package com.yoi.feign.service;

import com.yoi.entity.*;
import com.yoi.feign.service.feignimpl.FeignAdminServiceImpl;
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
 * 注意下面这玩意不仅要加requestparam，有的还要加requestbody，即使被调用的微服务没有这个要求也要加，而且注意力，里面填的都是value，一定不能填写name，不能填写name，除非到了sentinelResource
 */

@Component
@Validated
@FeignClient(name = "admin", fallback = FeignAdminServiceImpl.class)
public interface FeignAdminService {

    @CrossOrigin
    @GetMapping("/admin/{adminId}/{pageNo}/{pageSize}/{searchName}")
    ReturnInfo<PagePackage<Admin>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                         @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                         @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                         @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName);

    @PostMapping("/add_admin")
    ReturnInfo<Admin> addAdmin(@RequestBody  Admin admin);

    @DeleteMapping("/admin")
     ReturnInfo<Admin> deleteAdmin(@RequestBody @Valid Admin admin);

    @GetMapping("/admin/{adminId}")
     ReturnInfo<Admin> alterAdmin(@NotNull @PathVariable("adminId") Long adminId);

    @PutMapping("/admin")
     ReturnInfo<Admin> updateAdmin(@RequestBody @Valid Admin admin);










    @PostMapping(value = "/login/admin")
    ReturnInfo<Admin> adminLogin(@RequestBody Admin admin);

    @PostMapping(value = "/login/user")
    ReturnInfo<User> userLogin( @RequestBody User user);

    @PostMapping("/login/shopkeeper")
    ReturnInfo<Shopkeeper> shopkeeperLogin(@RequestBody Shopkeeper shopkeeper) ;

    @GetMapping("/login/checkToken")
    ReturnInfo<String> checkToken(@RequestParam("token") String token);
}
