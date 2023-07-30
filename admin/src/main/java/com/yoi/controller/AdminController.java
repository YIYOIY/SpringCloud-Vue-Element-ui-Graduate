package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yoi.entity.Admin;
import com.yoi.entity.ReturnInfo;
import com.yoi.exception.SelfExcept;
import com.yoi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ReturnInfo index(HttpServletRequest req, @RequestParam(value = "searchName",required = false) String searchName, @RequestParam(value = "oper",required = false) String oper) {
        HttpSession session = req.getSession();
        String token = req.getHeader("token");
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
            return new ReturnInfo(200,"用户信息获取成功",admins);
        } catch (Exception e) {
            throw new SelfExcept("AdminController的index出现的问题 \t"+e);
        }
    }

    @PostMapping("/addAdmin")
    public ReturnInfo addAdmin(@RequestBody Admin admin) {
        try {
            boolean Admin = adminService.addAdmin(admin);
            if (Admin) {
                return new ReturnInfo(200,"添加管理员成功");
            } else {
                return new ReturnInfo(401,"添加管理员失败，管理员名称重复！");
            }
        } catch (Exception e) {
            throw new SelfExcept("AdminController的add出现的问题 \t"+e);
        }
    }


    @DeleteMapping("/admin")
    public ReturnInfo deleteAdmin(@RequestParam("adminId") Integer adminId) {
        try {
            boolean deleteAdmin = adminService.deleteAdmin(adminId);
            if (deleteAdmin) {
                return new ReturnInfo(200,"删除成功管理员成功");
            } else {
                return new ReturnInfo(200,"删除成功管理员失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("AdminController的delete出现的问题\t"+e);
        }
    }


    @GetMapping("/alterAdmin")
    public ReturnInfo alterAdmin(@RequestParam("adminId") Integer adminId) {
        try {
            Admin alterAdmin = adminService.getByAdminId(adminId);
            return new ReturnInfo(200,"获取管理员数据成功",alterAdmin);
        } catch (Exception e) {
            throw new SelfExcept("AdminController的alter获取数据回显出现的问题\t"+e);
        }
    }

    @PutMapping("/admin")
    public ReturnInfo updateAdmin(@RequestBody Admin admin) {
        try {
            boolean alterdmin = adminService.updateAdmin(admin);
            if (alterdmin) {
                return  new ReturnInfo(200,"更新成功管理员成功");
            } else {
                return new ReturnInfo(200,"更新成功管理员失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("AdminController的update出现的问题\t"+e);
        }
    }

}
