package com.yoi.feigncontroller;

import com.yoi.entity.Order;
import com.yoi.feign.FeignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
public class OrderFeignController {
    @Resource
    private FeignOrderService feignOrderService;
    @GetMapping("/userOrder")
    public List<Order> index(@RequestParam(value = "pageNo",required = false)Integer pageNo,@RequestParam(value = "userId",required = false) Integer userId){
        return feignOrderService.index(pageNo, userId);
    }
    @GetMapping("/userPageCount")
    public long userPageCount(@RequestParam(value = "userId",required = false)Integer userId){
        return feignOrderService.userPageCount(userId);
    }
    @GetMapping("/adminPageCount")
    public long adminPageCount(){
        System.out.println("adminOrder  activced");
        return feignOrderService.adminPageCount();
    }
    @GetMapping("/adminOrder")
    public List<Order> indexAdmin(@RequestParam(value = "pageNo",required = false)Integer pageNo){
        return feignOrderService.indexAdmin(pageNo);
    }

    @GetMapping("/order")
    public Order getOrderById(@RequestParam(value = "orderId",required = false)Integer orderId){
        return feignOrderService.getOrderById(orderId);
    }

    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        return feignOrderService.addOrder(order);
    }
    @DeleteMapping("/order")
    public ResponseEntity<String> deleteOrder(@RequestParam(value = "orderId",required = false)Integer orderId){
        return feignOrderService.deleteOrder(orderId);
    }

    @PutMapping("/buyOrder")
    public ResponseEntity<String> buybag(@RequestParam(value = "orderId",required = false)Integer orderId,@RequestParam(value = "num",required = false)Integer num,@RequestParam(value = "bookId",required = false)Integer bookId){
        return feignOrderService.buybag(orderId, num, bookId);
    }

}
