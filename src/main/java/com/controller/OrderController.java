package com.controller;

import com.entity.Order;
import com.exception.SelfExcept;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/userBag")
    @ResponseBody
    @CrossOrigin
    private List<Order> index(Integer pageNo, Integer userId) {
        if (pageNo == null) {
            pageNo = 1;
        }
        try {
            List<Order> order = orderService.userGetAll(userId, pageNo);
            return order;
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的index出现的问题");
        }
    }

    @GetMapping("/userPageCount")
    @ResponseBody
    private long userPageCount(Integer userId) {
        Long aLong = orderService.userGetCount(userId);
        return (aLong + 4) / 5;
    }

    @GetMapping("/adminPageCount")
    @ResponseBody
    private long adminPageCount() {
        Long aLong = orderService.adminGetCount();
        return (aLong + 4) / 5;
    }

    @ResponseBody
    @GetMapping("/adminBag")
    private List<Order> indexAdmin(Integer pageNo) {
        if (pageNo == null) {
            pageNo = 1;
        }
        try {
            return orderService.adminGetAll(pageNo);
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的adminIndex出现的问题");
        }
    }


    @PostMapping("/bag")
    public ResponseEntity<String> addBag(@RequestBody Order order) {
        try {
            boolean addBag = orderService.addBag(order);
            if (addBag) {
                return ResponseEntity.ok("添加成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的add出现的问题");
        }
    }

    @DeleteMapping("/bag")
    public ResponseEntity<String> deleteBag(Integer bagId) {
        try {
            boolean addBag = orderService.deleteBag(bagId);
            if (addBag) {
                return ResponseEntity.ok("删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的delete出现的问题");
        }
    }
    @DeleteMapping("/buybag")
    public ResponseEntity<String> buybag(Integer bagId) {
        try {
            boolean addBag = orderService.deleteBag(bagId);
            if (addBag) {
                return ResponseEntity.ok("购买成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("购买失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的delete出现的问题");
        }
    }

}
