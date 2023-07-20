package com.yoi.feign;

import com.yoi.entity.Order;
import com.yoi.feign.feignimpl.FeignOrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@FeignClient(value = "orderMirco",fallback = FeignOrderServiceImpl.class)
public interface FeignOrderService {
    @GetMapping("/userOrder")
    public List<Order> index(@RequestParam(value = "pageNo",required = false)Integer pageNo,@RequestParam(value = "userId",required = false) Integer userId);
    @GetMapping("/userPageCount")
    public long userPageCount(@RequestParam(value = "userId",required = false)Integer userId);
    @GetMapping("/adminPageCount")
    public long adminPageCount();
    @GetMapping("/adminOrder")
    public List<Order> indexAdmin(@RequestParam(value = "pageNo",required = false)Integer pageNo);
    @GetMapping("/order")
    public Order getOrderById(@RequestParam(value = "orderId",required = false)Integer orderId);
    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody Order order);
    @DeleteMapping("/order")
    public ResponseEntity<String> deleteOrder(@RequestParam(value = "orderId",required = false)Integer orderId);
    @PutMapping("/buyOrder")
    public ResponseEntity<String> buybag(@RequestParam(value = "orderId",required = false)Integer orderId,@RequestParam(value ="num",required = false)Integer num,@RequestParam(value = "bookId",required = false)Integer bookId);
}
