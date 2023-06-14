package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Order;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface OrderService extends IService<Order> {
    List<Order> userGetAll(Integer userId, Integer page);
    //    long getTotal(Connection connection, Integer userId);
    List<Order> adminGetAll(Integer page);

    boolean addOrder(Order order);
    boolean deleteOrder(Integer bagId);

    boolean updateOrder(Integer bagId,Integer num,Integer bookId);
    Long adminGetCount();
    Long userGetCount(Integer userId);

    Order getId(Integer orderId);
}
