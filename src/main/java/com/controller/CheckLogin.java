package com.controller;
import com.entity.Admin;
import com.entity.Bag;
import com.entity.User;
import com.exception.SelfExcept;
import com.service.AdminService;
import com.service.BagService;
import com.service.UserService;
import com.service.impl.AdminImpl;
import com.service.impl.BagImpl;
import com.service.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@RestController
public class CheckLogin {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private BagService bagService;

    @PostMapping("/admin")
    public Admin adminLogin(@RequestBody Admin admin) {
        try {
            Admin loginAdmin = adminService.getByLogin(admin);
            if (loginAdmin!= null) {
                return loginAdmin;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SelfExcept("CheckLogin的admin出现的问题");
        }
    }

    @PostMapping("/user")
    public User userLogin(@RequestBody User user) {
        try {
            User loginUser = userService.getByLogin(user);
            if (loginUser!=null) {
                return loginUser;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SelfExcept("CheckLogin的user出现的问题");
        }
    }

    @PostMapping("/checkLogin")
    public String checkLogin(HttpServletRequest req, Integer id, Integer bookNumber) {
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser==null) {
            return "login";
        } else {
            try {
                Bag bag1 = new Bag();
                bag1.setBookId(id);
                bag1.setBookNumber(bookNumber);
                bag1.setUserId(loginUser.getUserId());
                boolean addBag = bagService.addBag(bag1);
                if (addBag) {
                    return "redirect:bag.do";
                } else {
                    return "bag/index";
                }
            } catch(Exception e) {
                throw new SelfExcept("CheckLogin的checkLogin出现的问题");
            }
        }
    }
}
