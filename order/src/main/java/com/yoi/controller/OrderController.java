package com.yoi.controller;

import com.yoi.entity.Order;
import com.yoi.exception.SelfExcept;
import com.yoi.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/userOrder")
    public List<Order> index(Integer pageNo, Integer userId) {
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
    public long userPageCount(Integer userId) {
        Long aLong = orderService.userGetCount(userId);
        return (aLong + 4) / 5;
    }

    @GetMapping("/adminPageCount")
    public long adminPageCount() {
        Long aLong = orderService.adminGetCount();
        return (aLong + 4) / 5;
    }

    @GetMapping("/adminOrder")
    public List<Order> indexAdmin(Integer pageNo) {
        System.out.println(pageNo);
            return orderService.adminAll(pageNo);
    }


    @GetMapping("/order")
    public Order getOrderById(Integer orderId){
        Order byId = orderService.getId(orderId);
        return byId;
    }


    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            boolean addBag = orderService.addOrder(order);
            if (addBag) {
                return ResponseEntity.ok("添加成功");
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

    @GlobalTransactional(rollbackFor = Exception.class)
    @PutMapping("/buyOrder")
    public ResponseEntity<String> buybag(Integer orderId,Integer num,Integer bookId) {
        try {
            boolean addBag = orderService.updateOrder(orderId,num,bookId);
            if (addBag) {
                stringRedisTemplate.delete("books");
                return ResponseEntity.ok("购买成功");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("购买失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的delete出现的问题");
        }
    }

}
