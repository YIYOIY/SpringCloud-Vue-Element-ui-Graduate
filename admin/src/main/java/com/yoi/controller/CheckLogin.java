package com.yoi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoi.entity.Admin;
import com.yoi.entity.Order;
import com.yoi.entity.User;
import com.yoi.exception.SelfExcept;
import com.yoi.service.AdminService;
import com.yoi.service.OrderService;
import com.yoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckLogin {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
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
            throw new SelfExcept("CheckLogin的admin出现的问题"+e);
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

//    @PostMapping("/checkLogin")
//    public String checkLogin(HttpServletRequest req, Integer id, Integer bookNumber) {
//        HttpSession session = req.getSession();
//        User loginUser = (User) session.getAttribute("loginUser");
//        if (loginUser==null) {
//            return "login";
//        } else {
//            try {
//                Order order1 = new Order();
//                order1.setBookId(id);
//                order1.setUserId(loginUser.getUserId());
//                boolean addBag = orderService.addOrder(order1);
//                if (addBag) {
//                    return "redirect:bag.do";
//                } else {
//                    return "bag/index";
//                }
//            } catch(Exception e) {
//                throw new SelfExcept("CheckLogin的checkLogin出现的问题");
//            }
//        }
//    }
}
