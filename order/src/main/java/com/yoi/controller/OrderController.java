package com.yoi.controller;

import com.yoi.entity.Order;
import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.exception.SelfExcept;
import com.yoi.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/userOrder")
    public ReturnInfo index(@RequestParam(value = "pageNo",required = false)Integer pageNo, @RequestParam(value = "userId",required = false) Integer userId) {
        if (pageNo == null) {
            pageNo = 1;
        }
        try {
            List<Order> order = orderService.userGetAll(userId, pageNo);
            return new ReturnInfo(200, "获取用户购物车数据成功！", order);
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的index出现的问题");
        }
    }

    @GetMapping("/userPageCount")
    public ReturnInfo userPageCount(@RequestParam(value = "userId",required = false)Integer userId) {
        Long aLong = orderService.userGetCount(userId);
        return new ReturnInfo(200, "获取页面数量成功", ((aLong + 4) / 5));
    }

    @GetMapping("/adminPageCount")
    public ReturnInfo adminPageCount() {
        Long aLong = orderService.adminGetCount();
        return new ReturnInfo(200, "获取页面数量成功", ((aLong + 4) / 5));
    }

    @GetMapping("/adminOrder")
    public ReturnInfo indexAdmin(@RequestParam("pageNo") Integer pageNo) {
        System.out.println(pageNo);
        return new ReturnInfo(200, "获取管理员权限下数据成功！", orderService.adminAll(pageNo));
    }


    @GetMapping("/order")
    public ReturnInfo getOrderById(@RequestParam(value = "orderId",required = false)Integer orderId) {
        Order byId = orderService.getId(orderId);
        return new ReturnInfo(200, "获取数据成功！", byId);
    }


    @PostMapping("/order")
    public ReturnInfo addOrder(@RequestBody Order order) {
        try {
            boolean addBag = orderService.addOrder(order);
            if (addBag) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ADD_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ADD_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的add出现的问题");
        }
    }

    @DeleteMapping("/order")
    public ReturnInfo deleteOrder(@RequestParam(value = "orderId",required = false)Integer orderId) {
        try {
            boolean addBag = orderService.deleteOrder(orderId);
            if (addBag) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的delete出现的问题");
        }
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @PutMapping("/buyOrder")
    public ReturnInfo buybag(@RequestParam(value = "orderId",required = false)Integer orderId,@RequestParam(value = "num",required = false) Integer num,@RequestParam(value = "bookId",required = false) Integer bookId) {
        try {
            boolean addBag = orderService.updateOrder(orderId, num, bookId);
            if (addBag) {
                stringRedisTemplate.delete("books");
                return new ReturnInfo(200, "购买成功！");
            } else {
                return new ReturnInfo(404, "购买失败！");
            }
        } catch (Exception e) {
            throw new SelfExcept(e + "bagController的delete出现的问题");
        }
    }

}
