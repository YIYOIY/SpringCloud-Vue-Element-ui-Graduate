package com.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.entity.User;
import com.exception.SelfExcept;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @ResponseBody
    @GetMapping("/users")
    public List<User> index(HttpServletRequest req, String searchName, String oper) {
        HttpSession session = req.getSession();
        try {
            if (StringUtils.isNotEmpty(oper) && oper.equals("getByName")) {
                if (StringUtils.isEmpty(searchName)) {
                    searchName = "";
                }
                session.setAttribute("Userkeyword", searchName);
            } else {
                String keyword = (String) session.getAttribute("Userkeyword");
                if (StringUtils.isNotEmpty(keyword)) {
                    searchName = keyword;
                } else {
                    searchName = "";
                }
            }

            List<User> users = userService.getAll(searchName);

            return users;
        } catch (Exception e) {
            throw new SelfExcept("userController的index出现的问题");
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public User userSelf(Integer userId) {
        try {
            User user = userService.getById(userId);
            return user;
        } catch (Exception e) {
            throw new SelfExcept("userController的userSelf出现的问题");
        }
    }
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            boolean addUser = userService.addUser(user);
            if (addUser) {
                return ResponseEntity.ok("添加成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("userController的addUser出现的问题");
        }
    }


    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(Integer userId) {
        try {
//            删除前检查购物车
//            Long aLong = userService.checkBagData(userId);
//            if (aLong > 0) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败,用户有未购买的书籍,请先清空购物车!");
//            } else {
                boolean deleteUser = userService.deleteUser(userId);
                if (deleteUser) {
                    return ResponseEntity.ok("删除成功");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
                }
//            }

        } catch (Exception e) {
            throw new SelfExcept("userController的deleteUser出现的问题");
        }
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            System.out.println(user);
            boolean alterUser = userService.updateUser(user);
            if (alterUser) {
                return ResponseEntity.ok("更新成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
            }
        } catch (Exception e) {
            throw new SelfExcept("userController的updateUser出现的问题"+e);
        }
    }

    //    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//    public String signUser(HttpServletRequest req, String userName, String userPetName, String userPassword, String userTel, String userAddress) {
//        Connection getconnection = null;
//        try {
//            getconnection = ConnectionUtil.getConn();
//            User user1 = new User(userName, userPassword, userPetName, userAddress, userTel);
//            boolean addUser = user.addUser(getconnection, user1);
//            if (addUser) {
//                return "redirect:book.do";
//            }
//        } catch (Exception e) {
//            throw new SelfExcept("userController的signUser出现的问题");
//        } finally {
////            GetDriver.closeresource(getconnection, null);
//            return "book/index";
//        }
//    }

//    这个是否和获取单个用户的方法所相同???????????????????????????????????????????????????????????????
//    @GetMapping("/alterUser")
//    @ResponseBody
//    public User alterUser(Integer userId) {
//        try {
//            User user = userService.getById(userId);
//            return user;
//        } catch (Exception e) {
//            throw new SelfExcept("userController的alterUser出现的问题");
//        }
//    }

    //是否和getuser相同?????????????????????????????????????????????????????????????????????????
//    public String adminAlterUser(HttpServletRequest req, Integer userId) {
//        Connection getconnection = null;
//        try {
//            getconnection = ConnectionUtil.getConn();
//            User byId = user.getById(getconnection, userId);
//            HttpSession session = req.getSession();
//            session.setAttribute("editUser", byId);
//            return "user/adminAlterUser";
//        } catch (Exception e) {
//            throw new SelfExcept("userController的alterUser出现的问题");
//        } finally {
////            GetDriver.closeresource(getconnection, null);
//        }
//    }


    //感觉和updateuser相同,从而注释!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//    public String AdminupdateUser(HttpServletRequest req, Integer userId, String userName, String userPetName, String userPassword, String userTel, String userAddress) {
//        Connection getconnection = null;
//        try {
//            getconnection = ConnectionUtil.getConn();
//            User user1 = new User(userId, userName, userPassword, userPetName, userAddress, userTel);
//            boolean alterUser = user.updateUser(getconnection, user1);
//            if (alterUser) {
//                return "redirect:user.do";
//            } else {
//                return "user/adminAlterUser";
//            }
//        } catch (Exception e) {
//            throw new SelfExcept("userController的updateUser出现的问题");
//        } finally {
////            GetDriver.closeresource(getconnection, null);
//        }
//    }
}
