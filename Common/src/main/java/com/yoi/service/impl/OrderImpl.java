package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Book;
import com.yoi.entity.Order;
import com.yoi.enumvalue.CommentEnum;
import com.yoi.enumvalue.OrderEnum;
import com.yoi.mapper.BookMapper;
import com.yoi.mapper.OrderMapper;
import com.yoi.mapper.UserMapper;
import com.yoi.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class OrderImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
//    使用resource原生，先byName，再byType
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private UserMapper userMapper;

    /*
     * 通过order表的bookId将购买的书籍信息一同放入order实体类中
     * */
    @Override
    public Order getById(Long orderId) {
        Order order = orderMapper.selectById(orderId);

        order.setBook(bookMapper.selectById(new QueryWrapper<Book>()
                        .eq("book_id", order.getBookId())
                )
        );

        return order;
    }

    /*
     * 用户查看自己的购物车
     * */
    @Override
    public Page<Order> userGetAll(Long userId,Integer pageNo,Integer pageSize) {
        Page<Order> orderPage = new Page<>(pageNo, pageSize);
        Page<Order> orderPage1 = orderMapper.selectPage(orderPage, new QueryWrapper<Order>().eq("user_id", userId));
        return getOrderPage(orderPage1);
    }

    /*
     * 管理员查看所有人的购物车
     * */
    @Override
    public Page<Order> adminGetAll(Integer pageNo, Integer pageSize) {
        Page<Order> orderPage = new Page<>(pageNo, pageSize);
        Page<Order> orderPage1 = orderMapper.selectPage(orderPage,new QueryWrapper<Order>().isNotNull("book_id").isNotNull("user_id"));
        return getOrderPage(orderPage1);
    }

    private Page<Order> getOrderPage(Page<Order> orderPage1) {
        List<Order> records = orderPage1.getRecords();
        records.forEach(order -> {
            order.setBook(bookMapper.selectById(order.getBookId()));
            order.setUser(userMapper.selectById(order.getUserId()));
        });
        orderPage1.setRecords(records);
        return orderPage1;
    }

    /*添加订单*/
    @Override
    public boolean addOrder(Order order) {
        return orderMapper.insert(order) > 0;
    }

    /*删除订单*/
    @Override
    public boolean deleteOrder(Long orderId) {
        return orderMapper.deleteById(orderId) > 0;
    }

    /*更新订单(购买)
     * 设置书籍数量同步更新，同时核查书籍库存是否足够用户购买数量，书籍数量扣除购买数量
     * 设置订单购买时间。
     * 设置订单状态为已购买
     * 设置评论为禁止评论
     *
     * 用户扣款
     * */
    @Override
    public boolean updateOrder(Long orderId, Integer num, Long bookId) {
        Book book = bookMapper.selectOne(new QueryWrapper<Book>().select("book_number").eq("book_id", bookId));
        if (book.getBookNumber() < num) {
            return false;
        }

        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<Book>()
                .set(num > 0, "book_number", book.getBookNumber() - num)
                .eq("book_id", bookId);


        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("buy_time", LocalDateTime.now())
                .set("order_status", OrderEnum.BUY)
                .set("comment_status", CommentEnum.COMMENTEDFORBIDDEN)
                .eq("order_id", orderId);
        return (orderMapper.update(null, orderUpdateWrapper) > 0 && bookMapper.update(null, bookUpdateWrapper) > 0);
    }

    /*更新订单(收货)
     * 设置书籍状态为已签收，
     * 设置签收时间。
     * 设置评论状态为可评论
     *
     * 商家收款，管理员收款  根据订单的价格，回扣，运费，折扣
     * */
    @Override
    public boolean confirmOrder(Long orderId) {
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("confirm_time", LocalDateTime.now())
                .set("order_status", OrderEnum.CONFIRM)
                .set("comment_status", CommentEnum.NOTCOMMENT)
                .eq("order_id", orderId);
        return (orderMapper.update(null, orderUpdateWrapper) > 0);
    }

    /*更新订单(退货)
     * 设置书籍数量同步更新，将用户订单的书籍数量退回书籍
     * 设置订单状态为已退货
     * 设置退货时间
     * 设置评论为不可评论
     *
     * 用户退款，管理员退款，商家退款（已签收）根据订单的价格，回扣，运费，折扣
     * 用户退款（未签收）
     * */
    @Override
    public boolean backOrder(Long orderId) {
        Order byId = getById(orderId);
        Book book = bookMapper.selectOne(new QueryWrapper<Book>().select("book_number").eq("book_id", byId.getBookId()));
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<Book>()
                .set(byId.getBuyNumber() > 0, "book_number", book.getBookNumber() + byId.getBuyNumber())
                .eq("book_id", byId.getBookId());


        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("back_time", LocalDateTime.now())
                .set("order_status", OrderEnum.BACKING)
                .set("comment_status", CommentEnum.COMMENTEDFORBIDDEN)
                .eq("order_id", orderId);
        return (orderMapper.update(null, orderUpdateWrapper) > 0 && bookMapper.update(null, bookUpdateWrapper) > 0);
    }

}
//测试全局回滚用的
//        try {
//            TimeUnit.SECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e+"超时了");
//        }