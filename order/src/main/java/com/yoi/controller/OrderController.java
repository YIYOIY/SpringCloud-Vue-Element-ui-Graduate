package com.yoi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.Order;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RefreshScope
@Slf4j
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @NonNull
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/user_order/{pageNo}/{pageSize}/{userId}")
    public ReturnInfo<PagePackage<Order>> userListOrder(@Min(1) @PathVariable("pageNo") Integer pageNo,
                                                        @Min(5) @PathVariable("pageSize") Integer pageSize,
                                                        @PathVariable("userId") Long userId) {
        Page<Order> orderPage = orderService.userGetAll(userId, pageNo, pageSize);
        return getPagePackageReturnInfo(orderPage);
    }

    @GetMapping("/admin_order/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Order>> adminListOrder(@Min(1) @PathVariable("pageNo") Integer pageNo,
                                                         @Min(5) @PathVariable("pageSize") Integer pageSize) {
        Page<Order> adminPage = orderService.adminGetAll(pageNo, pageSize);
        return getPagePackageReturnInfo(adminPage);
    }

    private ReturnInfo<PagePackage<Order>> getPagePackageReturnInfo(Page<Order> adminPage) {
        if (adminPage.getRecords() != null) {
            PagePackage<Order> orderPagePackage = new PagePackage<>(adminPage.getRecords(), adminPage.getCurrent()
                    , adminPage.getPages(), adminPage.getSize(), adminPage.getTotal(), adminPage.hasNext(), adminPage.hasPrevious());
            return new ReturnInfo<>(200, "获取用户购物车数据成功！", orderPagePackage);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.GET_FAILED);
        }
    }


    @GetMapping("/order/{orderId}")
    public ReturnInfo<Order> getOrderById(@NotNull @PathVariable("orderId") Long orderId) {
        Order byId = orderService.getById(orderId);
        if (byId != null) {
            return new ReturnInfo<>(200, "获取数据成功！", byId);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.GET_FAILED);
        }
    }


    @PostMapping("/order")
    public ReturnInfo<String> addOrder(@RequestBody Order order) {
        if (orderService.addOrder(order)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_FAILED);
        }
    }

    @DeleteMapping("/order")
    public ReturnInfo<String> deleteOrder(@Valid @RequestBody Order order) {
        if (orderService.deleteOrder(order.getId())) {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @PutMapping("/order")
//    public ReturnInfo<String> buy(@RequestParam(value = "orderId", required = false) Integer orderId, @RequestParam(value = "num", required = false) Integer num, @RequestParam(value = "bookId", required = false) Integer bookId) {
    public ReturnInfo<String> buy(@Valid @RequestBody Order order) {
        if (orderService.updateOrder(order.getId(), order.getBuyNumber(), order.getBookId())) {
            stringRedisTemplate.delete("books");
            return new ReturnInfo<>(200, "购买成功！");
        } else {
            return new ReturnInfo<>(404, "购买失败！");
        }
    }

    @PutMapping("/confirm_order")
    public ReturnInfo<String> confirmOrder(@Valid @RequestBody Order order) {
        if (orderService.confirmOrder(order.getId())) {
            stringRedisTemplate.delete("books");
            return new ReturnInfo<>(200, "收货成功！");
        } else {
            return new ReturnInfo<>(404, "收货失败！");
        }
    }
    @PutMapping("/back_order")
    public ReturnInfo<String> backOrder(@Valid @RequestBody Order order) {
        if (orderService.backOrder(order.getId())) {
            stringRedisTemplate.delete("books");
            return new ReturnInfo<>(200, "退货成功！");
        } else {
            return new ReturnInfo<>(404, "退货失败！");
        }
    }

}
