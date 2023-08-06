package com.yoi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Order;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface OrderService extends IService<Order> {
    Order getById(Long orderId);
    Page<Order> userGetAll(Long userId, Integer pageNo, Integer pageSize);
    Page<Order> adminGetAll(Integer pageNo, Integer pageSize);

    boolean addOrder(Order order);
    boolean deleteOrder(Long orderId);
    boolean updateOrder(Long orderId,Integer num,Long bookId);
    boolean confirmOrder(Long orderId);
    boolean backOrder(Long orderId);
}
