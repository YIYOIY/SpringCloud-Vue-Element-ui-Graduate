package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Book;
import com.yoi.entity.Order;
import com.yoi.entity.User;
import com.yoi.mapper.BookMapper;
import com.yoi.mapper.OrderMapper;
import com.yoi.mapper.UserMapper;
import com.yoi.service.OrderService;
import org.apache.ibatis.ognl.enhance.OrderedReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class OrderImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Order> userGetAll(Integer userId, Integer page) {
        List<Order> orders = orderMapper.selectList(new QueryWrapper<Order>().eq("user_id",userId));
        orders.forEach(order -> {
            Book book = bookMapper.selectById(order.getBookId());
            order.setBook(book);
            User byId = userMapper.getById(order.getUserId());
            order.setUser(byId);
        });
        return orders;
    }

    @Override
    public List<Order> adminAll(Integer page) {
//        int pageStart=(page-1)*5;
//        return orderMapper.adminGetAll(pageStart);
        QueryWrapper<Order> notNull = new QueryWrapper<Order>().isNotNull("book_id").isNotNull("order_id");
        List<Order> orders = Collections.unmodifiableList(orderMapper.selectList(notNull));
                orders.forEach(order -> {
                    Book book = bookMapper.selectById(order.getBookId());
                    order.setBook(book);
                    User byId = userMapper.getById(order.getUserId());
                    order.setUser(byId);
                });
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
        //        设置书籍数量同步更新，同时核查书籍库存是否足够用户购买数量
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Book> eq1 = bookQueryWrapper.select("book_num").eq("book_id", bookId);
        Book book = bookMapper.selectOne(eq1);
        if(book.getBookNum()<num){
            return false;
        }
//        设置书籍数量更新
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Book> eq = bookUpdateWrapper.set("book_num", book.getBookNum()-num).eq("book_id", bookId);

//测试全局回滚用的
//        try {
//            TimeUnit.SECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e+"超时了");
//        }


        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Order> up = orderUpdateWrapper.set("order_status", "已购买").eq("order_id", orderId);
        return (orderMapper.update(null,up)>0 && bookMapper.update(null,eq)>0);
    }

    @Override
    public Long adminGetCount() {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.isNotNull("user_id").isNotNull("book_id");
        return orderMapper.selectCount(orderQueryWrapper);
    }

    @Override
    public Long userGetCount(Integer userId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.isNotNull("user_id").isNotNull("book_id").eq("user_id",userId);
        return orderMapper.selectCount(orderQueryWrapper);
    }

    @Override
    public Order getId(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        QueryWrapper<Book> objectQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Book> eq = objectQueryWrapper.eq("book_id", order.getBookId());
        Book book = bookMapper.selectOne(eq);
        order.setBook(book);
        return order;
    }
}
