package com.yoi.feigncontroller;

import com.yoi.entity.*;
import com.yoi.feign.service.FeignAdminService;
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

@RestController
@RefreshScope
@Validated
public class AdminFeignController {
    @Resource
    private FeignAdminService feignAdminService;

    @CrossOrigin
    @GetMapping("/admin/{adminId}/{pageNo}/{pageSize}/{searchName}/{operate}")
    public ReturnInfo<PagePackage<Admin>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                                @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                                @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                                @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName,
                                                @Length(max = 100) @PathVariable(value = "operate", required = false) String operate) {
        return feignAdminService.index(adminId, pageNo, pageSize, searchName, operate);
    }

    @PostMapping("/add_admin")
    public ReturnInfo<Admin> addAdmin(@RequestBody Admin admin) {
        return feignAdminService.addAdmin(admin);
    }

    @DeleteMapping("/admin")
    public ReturnInfo<Admin> deleteAdmin(@RequestBody @Valid Admin admin) {
        return feignAdminService.deleteAdmin(admin);
    }

    @GetMapping("/admin/{adminId}")
    public ReturnInfo<Admin> alterAdmin(@NotNull @PathVariable("adminId") Long adminId) {
        return feignAdminService.alterAdmin(adminId);
    }

    @PutMapping("/admin")
    public ReturnInfo<Admin> updateAdmin(@RequestBody @Valid Admin admin) {
        return feignAdminService.updateAdmin(admin);
    }


    @PostMapping(value = "/login/admin")
    public ReturnInfo<Admin> adminLogin( @RequestBody Admin admin) {
        return feignAdminService.adminLogin(admin);
    }

    @PostMapping(value = "/login/user")
    public ReturnInfo<User> userLogin( @RequestBody User user) {
        return feignAdminService.userLogin(user);
    }

    @PostMapping("/login/shopkeeper")
    public ReturnInfo<Shopkeeper> shopkeeperLogin(@RequestBody Shopkeeper shopkeeper) {
        System.out.println(shopkeeper);
        return feignAdminService.shopkeeperLogin(shopkeeper);
    }

    @GetMapping("/login/checkToken")
    public ReturnInfo<String> checkToken(@RequestParam("token") String token) {
        return feignAdminService.checkToken(token);
    }

}
