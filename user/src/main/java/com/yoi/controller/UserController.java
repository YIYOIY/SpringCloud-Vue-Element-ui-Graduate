package com.yoi.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.exception.SelfExcept;
import com.yoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/users")
    public ReturnInfo index(HttpServletRequest req, String searchName, String oper) {
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
            return new ReturnInfo(200,"获取用户列表成功！",users);
        } catch (Exception e) {
            throw new SelfExcept("userController的index出现的问题");
        }
    }

    @GetMapping("/user")
    public ReturnInfo userSelf(Integer userId) {
        try {
            User user = userService.getById(userId);
            return new ReturnInfo(200,"获取用户信息成功！",user);
        } catch (Exception e) {
            throw new SelfExcept("userController的userSelf出现的问题");
        }
    }
    @PostMapping("/addUser")
    public ReturnInfo addUser(@RequestBody User user) {
        try {
            boolean addUser = userService.addUser(user);
            if (addUser) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.USER_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.USER_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept("userController的addUser出现的问题");
        }
    }


    @DeleteMapping("/user")
    public ReturnInfo deleteUser(Integer userId) {
        try {
//            删除前检查购物车
//            Long aLong = userService.checkBagData(userId);
//            if (aLong > 0) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败,用户有未购买的书籍,请先清空购物车!");
//            } else {
                boolean deleteUser = userService.deleteUser(userId);
                if (deleteUser) {
                    return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_SUCCESS);
                } else {
                    return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_FAILED);
                }
//            }

        } catch (Exception e) {
            throw new SelfExcept("userController的deleteUser出现的问题"+e);
        }
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PutMapping("/user")
    public ReturnInfo updateUser(@RequestBody User user) {
        try {
            System.out.println(user);
            boolean alterUser = userService.updateUser(user);
            if (alterUser) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept("userController的updateUser出现的问题"+e);
        }
    }

}
