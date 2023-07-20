package com.yoi.feign;

import com.yoi.entity.Admin;
import com.yoi.entity.User;
import com.yoi.feign.feignimpl.FeignAdminServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 * 注意下面这玩意不仅要加requestparam，有的还要加requestbody，即使被调用的微服务没有这个要求也要加，而且注意力，里面填的都是value，一定不能填写name，不能填写name，除非到了sentinelResource
 */
@Component
@FeignClient(name = "admin",fallback = FeignAdminServiceImpl.class)
public interface FeignAdminService {
    @GetMapping(value = "/admin")
    public List<Admin> index( @RequestParam(value = "req",required = false) HttpServletRequest req, @RequestParam(value = "searchName",required = false) String searchName,@RequestParam(value = "oper",required = false) String oper);
    @PostMapping(value = "/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin);
    @DeleteMapping(value = "/admin")
    public ResponseEntity<String> deleteAdmin(@RequestParam(value = "adminId",required = false)Integer adminId);
    @GetMapping(value = "/alterAdmin")
    public Admin alterAdmin(@RequestParam(value = "adminId",required = false)Integer adminId);
    @PutMapping(value = "/admin")
    public ResponseEntity<String> updateAdmin(@RequestBody Admin admin);

    @PostMapping(value = "/admin")
    public Admin adminLogin(@RequestBody Admin admin);
    @PostMapping(value = "/user")
    public User userLogin(@RequestBody User user);

}
