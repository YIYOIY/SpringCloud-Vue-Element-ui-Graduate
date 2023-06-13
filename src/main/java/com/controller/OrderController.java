package com.controller;

import com.entity.Order;
import com.exception.SelfExcept;
import com.mapper.BookMapper;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @GetMapping("/userOrder")
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
    @GetMapping("/adminOrder")
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


    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            boolean addBag = orderService.addOrder(order);
            if (addBag) {
                return ResponseEntity.ok("购买成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的add出现的问题");
        }
    }

    @DeleteMapping("/order")
    public ResponseEntity<String> deleteOrder(Integer orderId) {
        try {
            boolean addBag = orderService.deleteOrder(orderId);
            if (addBag) {
                return ResponseEntity.ok("删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的delete出现的问题");
        }
    }
    @PutMapping("/buyOrder")
    public ResponseEntity<String> buybag(Integer orderId,Integer num,Integer bookId) {
        try {
            stringRedisTemplate.delete("books");
            boolean addBag = orderService.updateOrder(orderId,num,bookId);
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
