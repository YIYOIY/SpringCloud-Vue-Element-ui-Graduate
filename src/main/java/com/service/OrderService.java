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

    boolean addBag(Order order);
    boolean deleteBag(Integer bagId);
    //    boolean updateBag(Connection connection,entity.Bag bag);
    Long adminGetCount();
    Long userGetCount(Integer userId);
}
