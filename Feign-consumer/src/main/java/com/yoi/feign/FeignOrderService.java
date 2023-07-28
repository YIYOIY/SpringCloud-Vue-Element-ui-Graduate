package com.yoi.feign;

import com.yoi.entity.Order;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.feignimpl.FeignOrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@FeignClient(value = "orderMirco",fallback = FeignOrderServiceImpl.class)
public interface FeignOrderService {
    @GetMapping("/userOrder")
    public ReturnInfo index(@RequestParam(value = "pageNo",required = false)Integer pageNo, @RequestParam(value = "userId",required = false) Integer userId);
    @GetMapping("/userPageCount")
    public ReturnInfo userPageCount(@RequestParam(value = "userId",required = false)Integer userId);
    @GetMapping("/adminPageCount")
    public ReturnInfo adminPageCount();
    @GetMapping("/adminOrder")
    public ReturnInfo indexAdmin(@RequestParam(value = "pageNo",required = false)Integer pageNo);
    @GetMapping("/order")
    public ReturnInfo getOrderById(@RequestParam(value = "orderId",required = false)Integer orderId);
    @PostMapping("/order")
    public ReturnInfo addOrder(@RequestBody Order order);
    @DeleteMapping("/order")
    public ReturnInfo deleteOrder(@RequestParam(value = "orderId",required = false)Integer orderId);
    @PutMapping("/buyOrder")
    public ReturnInfo buybag(@RequestParam(value = "orderId",required = false)Integer orderId,@RequestParam(value ="num",required = false)Integer num,@RequestParam(value = "bookId",required = false)Integer bookId);
}
