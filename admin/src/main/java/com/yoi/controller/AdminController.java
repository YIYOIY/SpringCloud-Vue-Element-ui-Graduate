package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yoi.entity.Admin;
import com.yoi.exception.SelfExcept;
import com.yoi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @CrossOrigin
    @GetMapping("/admin")
    public List<Admin> index(HttpServletRequest req,@RequestParam(value = "searchName",required = false) String searchName,@RequestParam(value = "oper",required = false) String oper) {
        HttpSession session = req.getSession();
        try {
            if (StringUtils.isNotEmpty(oper) && oper.equals("getByName")) {
                if (StringUtils.isEmpty(searchName)) {
                    searchName = "";
                }
                session.setAttribute("Adminkeyword", searchName);
            } else {
                String keyword = (String) session.getAttribute("Adminkeyword");
                if (StringUtils.isNotEmpty(keyword)) {
                    searchName = keyword;
                } else {
                    searchName = "";
                }
            }
            List<Admin> admins = adminService.getAll(searchName);
            return admins;
        } catch (Exception e) {
            throw new SelfExcept("AdminController的index出现的问题");
        }
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        try {
            System.out.println(admin+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            boolean Admin = adminService.addAdmin(admin);
            if (Admin) {
                return ResponseEntity.ok("添加成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("AdminController的add出现的问题");
        }
    }


    @DeleteMapping("/admin")
    public ResponseEntity<String> deleteAdmin(Integer adminId) {
        try {
            boolean deleteAdmin = adminService.deleteAdmin(adminId);
            if (deleteAdmin) {
                return ResponseEntity.ok("删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("AdminController的delete出现的问题");
        }
    }


    @GetMapping("/alterAdmin")
    public Admin alterAdmin(Integer adminId) {
        try {
            Admin alterAdmin = adminService.getByAdminId(adminId);
            return alterAdmin;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/admin")
    public ResponseEntity<String> updateAdmin(@RequestBody Admin admin) {
        try {
            boolean alterdmin = adminService.updateAdmin(admin);
            if (alterdmin) {
                return  ResponseEntity.ok("更新成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("AdminController的update出现的问题");
        }
    }

}
