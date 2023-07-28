package com.yoi.feigncontroller;

import com.yoi.entity.Admin;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.feign.FeignAdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */

@RestController
public class AdminFeignController {
    @Resource
    private FeignAdminService feignAdminService;
    @GetMapping("/admin")
    public ReturnInfo index(@RequestParam(value = "req",required = false)HttpServletRequest req, @RequestParam(value = "searchName",required = false)String searchName, @RequestParam(value = "oper",required = false)String oper){
        return feignAdminService.index(req, searchName, oper);
    }
    @PostMapping("/addAdmin")
    public ReturnInfo addAdmin(@RequestBody Admin admin){
        return feignAdminService.addAdmin(admin);
    }
    @DeleteMapping("/admin")
    public ReturnInfo deleteAdmin(@RequestParam(value = "adminId",required = false) Integer adminId){
        return feignAdminService.deleteAdmin(adminId);
    }
    @GetMapping("/alterAdmin")
    public ReturnInfo alterAdmin(@RequestParam(value = "adminId",required = false) Integer adminId){
        return feignAdminService.alterAdmin(adminId);
    }
    @PutMapping("/admin")
    public ReturnInfo updateAdmin(@RequestBody Admin admin){
        return feignAdminService.updateAdmin(admin);
    }

    @PostMapping(value = "/login/admin")
    public ReturnInfo adminLogin(@RequestBody Admin admin){
        return feignAdminService.adminLogin(admin);
    }
    @PostMapping(value = "/login/user")
    public ReturnInfo userLogin(@RequestBody User user){
        return feignAdminService.userLogin(user);
    }
    @GetMapping("/login/checkToken")
    public ReturnInfo userLogin(@RequestParam("token")String token) {
        return feignAdminService.userLogin(token);
    }
}
