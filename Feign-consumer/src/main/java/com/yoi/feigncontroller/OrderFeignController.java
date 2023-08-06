package com.yoi.feigncontroller;

import com.yoi.entity.Order;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.service.FeignOrderService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@Validated
@RestController
@RefreshScope
public class OrderFeignController {
    @Resource
    private FeignOrderService feignOrderService;

    @GetMapping("/user_order/{pageNo}/{pageSize}/{userId}")
    public ReturnInfo<PagePackage<Order>> userListOrder(@Min(1) @PathVariable("pageNo") Integer pageNo,
                                                        @Min(5) @PathVariable("pageSize") Integer pageSize,
                                                        @PathVariable("userId") Long userId) {
        return feignOrderService.userListOrder(pageNo, pageSize, userId);
    }

    @GetMapping("/admin_order/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Order>> adminListOrder(@Min(1) @PathVariable("pageNo") Integer pageNo,
                                                         @Min(5) @PathVariable("pageSize") Integer pageSize) {
        return feignOrderService.adminListOrder(pageNo, pageSize);
    }

    @GetMapping("/order/{orderId}")
    public ReturnInfo<Order> getOrderById(@NotNull @PathVariable("orderId") Long orderId) {
        return feignOrderService.getOrderById(orderId);
    }

    @PostMapping("/order")
    public ReturnInfo<String> addOrder(@RequestBody Order order) {
        return feignOrderService.addOrder(order);
    }

    @DeleteMapping("/order")
    public ReturnInfo<String> deleteOrder(@Valid @RequestBody Order order) {
        return feignOrderService.deleteOrder(order);
    }

    @PutMapping("/order")
    public ReturnInfo<String> buy(@Valid @RequestBody Order order) {
        return feignOrderService.buy(order);
    }

    @PutMapping("/confirm_order")
    public ReturnInfo<String> confirmOrder(@Valid @RequestBody Order order) {
        return feignOrderService.confirmOrder(order);
    }

    @PutMapping("/back_order")
    public ReturnInfo<String> backOrder(@Valid @RequestBody Order order) {
        return feignOrderService.backOrder(order);
    }
}
