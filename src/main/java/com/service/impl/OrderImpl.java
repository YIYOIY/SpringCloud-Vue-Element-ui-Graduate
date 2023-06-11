package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Book;
import com.entity.Order;
import com.entity.User;
import com.mapper.BookMapper;
import com.mapper.OrderMapper;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class OrderImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private BookMapper bookMapper;
    @Override
    public List<Order> userGetAll(Integer userId, Integer page) {
//        int pageStart=(page-1)*5;
//        return orderMapper.userGetAll(userId, pageStart);
//        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
//        QueryWrapper<Order> user = orderQueryWrapper.eq("user_id", userId);

        List<Order> orders = orderMapper.userGetAll(userId,page);
        System.out.println(orders);
        return orders;
    }

    @Override
    public List<Order> adminGetAll(Integer page) {
//        int pageStart=(page-1)*5;
//        return orderMapper.adminGetAll(pageStart);
//        List<Order> orders = orderMapper.selectList(null);
        List<Order> orders = orderMapper.adminGetAll(page);
        System.out.println(orders);
        return orders;
    }

    @Override
    public boolean addOrder(Order order) {
        return orderMapper.insert(order)>0;
    }

    @Override
    public boolean deleteOrder(Integer bagId) {
        return orderMapper.deleteById(bagId)>0;
    }

    @Override
    public boolean updateOrder(Integer orderId,Integer num,Integer bookId) {
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Order> up = orderUpdateWrapper.set("order_status", "已购买").eq("order_id", orderId);

//        设置书籍数量同步更新
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<>();
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Book> eq1 = bookQueryWrapper.select("book_num").eq("book_id", bookId);
        Book book = bookMapper.selectOne(eq1);
        UpdateWrapper<Book> eq = bookUpdateWrapper.set("book_num", book.getBookNum()-num).eq("book_id", bookId);

        return (orderMapper.update(null,up)>0 && bookMapper.update(null,eq)>0);
    }

    @Override
    public Long adminGetCount() {
        return orderMapper.selectCount(new QueryWrapper<Order>().isNotNull("user_id").and(i->i.isNotNull("book_id")));
    }

    @Override
    public Long userGetCount(Integer userId) {
        return orderMapper.selectCount(new QueryWrapper<Order>().eq("user_id",userId).and(i->i.isNotNull("book_id")));
    }
}
