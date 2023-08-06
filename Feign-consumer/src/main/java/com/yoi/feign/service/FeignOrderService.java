package com.yoi.feign.service;

import com.yoi.entity.Order;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.service.feignimpl.FeignOrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Validated
@Component
@FeignClient(value = "order", fallback = FeignOrderServiceImpl.class)
public interface FeignOrderService {
    @GetMapping("/user_order/{pageNo}/{pageSize}/{userId}")
    ReturnInfo<PagePackage<Order>> userListOrder(@Min(1) @PathVariable("pageNo") Integer pageNo,
                                                 @Min(5) @PathVariable("pageSize") Integer pageSize,
                                                 @PathVariable("userId") Long userId);

    @GetMapping("/admin_order/{pageNo}/{pageSize}")
    ReturnInfo<PagePackage<Order>> adminListOrder(@Min(1) @PathVariable("pageNo") Integer pageNo,
                                                  @Min(5) @PathVariable("pageSize") Integer pageSize);

    @GetMapping("/order/{orderId}")
    ReturnInfo<Order> getOrderById(@NotNull @PathVariable("orderId") Long orderId);

    @PostMapping("/order")
    ReturnInfo<String> addOrder(@RequestBody Order order);

    @DeleteMapping("/order")
    ReturnInfo<String> deleteOrder(@Valid @RequestBody Order order);

    @PutMapping("/order")
    ReturnInfo<String> buy(@Valid @RequestBody Order order);

    @PutMapping("/confirm_order")
    ReturnInfo<String> confirmOrder(@Valid @RequestBody Order order);

    @PutMapping("/back_order")
    ReturnInfo<String> backOrder(@Valid @RequestBody Order order);
}
