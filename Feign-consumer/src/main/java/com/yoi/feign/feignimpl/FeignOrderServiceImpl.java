package com.yoi.feign.feignimpl;

import com.yoi.entity.Order;
import com.yoi.feign.FeignOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignOrderServiceImpl implements FeignOrderService {
    @Override
    public List<Order> index(@RequestParam(value = "pageNo",required = false) Integer pageNo,@RequestParam(value = "userId",required = false)  Integer userId) {
        return null;
    }

    @Override
    public long userPageCount(Integer userId) {
        return 0;
    }

    @Override
    public long adminPageCount() {
        return 0;
    }

    @Override
    public List<Order> indexAdmin(Integer pageNo) {
        return null;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return null;
    }

    @Override
    public ResponseEntity<String> addOrder(Order order) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> deleteOrder(Integer orderId) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> buybag(Integer orderId, Integer num, Integer bookId) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }
}
