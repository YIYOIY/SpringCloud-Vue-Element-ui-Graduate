package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Order;
import com.mapper.OrderMapper;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class OrderImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public List<Order> userGetAll(Integer userId, Integer page) {
        int pageStart=(page-1)*5;
        return orderMapper.userGetAll(userId, pageStart);
    }

    @Override
    public List<Order> adminGetAll(Integer page) {
        int pageStart=(page-1)*5;
        return orderMapper.adminGetAll(pageStart);
    }

    @Override
    public boolean addBag(Order order) {
        return orderMapper.addBag(order);
    }

    @Override
    public boolean deleteBag(Integer bagId) {
        return orderMapper.deleteBag(bagId);
    }

    @Override
    public Long adminGetCount() {
        return orderMapper.adminGetCount();
    }

    @Override
    public Long userGetCount(Integer userId) {
        return orderMapper.userGetCount(userId);
    }
}
