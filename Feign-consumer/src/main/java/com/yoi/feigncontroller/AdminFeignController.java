package com.yoi.feigncontroller;

import com.yoi.entity.Admin;
import com.yoi.entity.User;
import com.yoi.feign.FeignAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
public class AdminFeignController {
    @Resource
    private FeignAdminService feignAdminService;
    @GetMapping("/admin")
    public List<Admin> index(@RequestParam(value = "req",required = false)HttpServletRequest req,@RequestParam(value = "searchName",required = false)String searchName,@RequestParam(value = "oper",required = false)String oper){
        return feignAdminService.index(req, searchName, oper);
    }
    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin){
        return feignAdminService.addAdmin(admin);
    }
    @DeleteMapping("/admin")
    public ResponseEntity<String> deleteAdmin(@RequestParam(value = "adminId",required = false) Integer adminId){
        return feignAdminService.deleteAdmin(adminId);
    }
    @GetMapping("/alterAdmin")
    public Admin alterAdmin(@RequestParam(value = "adminId",required = false) Integer adminId){
        return feignAdminService.alterAdmin(adminId);
    }
    @PutMapping("/admin")
    public ResponseEntity<String> updateAdmin(@RequestBody Admin admin){
        return feignAdminService.updateAdmin(admin);
    }

    @PostMapping(value = "/admin")
    public Admin adminLogin(@RequestBody Admin admin){
        return feignAdminService.adminLogin(admin);
    }
    @PostMapping(value = "/user")
    public User userLogin(@RequestBody User user){
        return feignAdminService.userLogin(user);
    }

//    ,consumes = "application/json"
}
