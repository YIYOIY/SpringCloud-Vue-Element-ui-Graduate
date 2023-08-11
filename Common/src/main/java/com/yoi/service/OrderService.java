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
    Page<Order> shopkeeperGetAll(Long shopkeeperId, Integer pageNo, Integer pageSize);
    Page<Order> adminGetAll(Integer pageNo, Integer pageSize);
    boolean addOrder(Order order);
    boolean deleteOrder(Order order);
    boolean updateOrder(Order order);
    boolean confirmOrder(Order order);
    boolean backOrder(Order order);
    boolean commentOrder(Order order);
    boolean alterOrder(Order order);
}
