package com.controller;
import com.entity.Admin;
import com.entity.Order;
import com.entity.User;
import com.exception.SelfExcept;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.AdminService;
import com.service.OrderService;
import com.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@RestController
public class CheckLogin {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private OrderService orderService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    private final  static ObjectMapper om = new ObjectMapper();

    @PostMapping("/admin")
    public Admin adminLogin(@RequestBody Admin admin) {
        try {
            Admin loginAdmin = adminService.getByLogin(admin);
            if (loginAdmin!= null) {
                String s = om.writeValueAsString(loginAdmin);
                stringRedisTemplate.opsForValue().set("admin",s);
                String s1 = stringRedisTemplate.opsForValue().get("admin");
//                Admin admin1 = om.readValue(s1, Admin.class);
//                System.out.println(admin1);
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
                Order order1 = new Order();
                order1.setBookId(id);
                order1.setUserId(loginUser.getUserId());
                boolean addBag = orderService.addOrder(order1);
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
