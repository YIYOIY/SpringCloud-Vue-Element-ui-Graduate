package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.*;
import com.yoi.enumvalue.CommentEnum;
import com.yoi.enumvalue.OrderEnum;
import com.yoi.mapper.AdminMapper;
import com.yoi.mapper.BookMapper;
import com.yoi.mapper.OrderMapper;
import com.yoi.mapper.WordMapper;
import com.yoi.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private WordMapper wordMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;
    @Resource
    private WordService wordService;
    @Resource
    private ShopkeeperService shopkeeperService;
    @Resource
    private AdminService adminService;

    /*
     * 通过order表的id将购买的信息一同放入order实体类中
     * */
    @Override
    public Order getById(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        order.setBook(bookService.getById(order.getBookId()));
        order.setUser(userService.getById(order.getUserId()));
        order.setWord(wordService.getById(order.getWordId()));
        return order;
    }

    /*
     * 用户查看自己的购物车
     * */
    @Override
    public Page<Order> userGetAll(Long userId, Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>(pageNo, pageSize);
        Page<Order> orderPage = orderMapper.selectPage(page, new QueryWrapper<Order>().eq("user_id", userId));
        for (Order record : orderPage.getRecords()) {
            record.setBook(bookService.getById(record.getBookId()));
            record.setUser(userService.getById(record.getUserId()));
            record.setWord(wordService.getById(record.getWordId()));
        }
        return orderPage;
    }

    @Override
    public Page<Order> shopkeeperGetAll(Long shopkeeperId, Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>(pageNo, pageSize);
        List<Long> orders = new ArrayList<>();

//        查询出所有未支付的订单
        Page<Order> orderPage = orderMapper.selectPage(page, new QueryWrapper<Order>().eq("order_status", OrderEnum.NOTPAIED));

        for (Order record : orderPage.getRecords()) {
//            根据订单的书籍id和前端传递的企业id在book中查询订单中该企业的下的书籍
            Book book = bookMapper.selectOne(new QueryWrapper<Book>().eq("id", record.getBookId()).eq("shopkeeper_id", shopkeeperId));
//            如果书籍不为空，则表示该企业有订单
            if (book != null) {
                orders.add(record.getId());
            }
        }

//        根据收集的订单id在订单中重新分页查询
        Page<Order> shopkeeperOrder = orderMapper.selectPage(page, new QueryWrapper<Order>().in("id", orders));
        System.out.println("该企业的订单:" + shopkeeperOrder.getRecords().toString());
//       将企业的订单嵌入数据
        for (Order order : shopkeeperOrder.getRecords()) {
            order.setBook(bookService.getById(order.getBookId()));
            order.setUser(userService.getById(order.getUserId()));
            order.setWord(wordService.getById(order.getWordId()));
        }
        return shopkeeperOrder;
    }

    /*
     * 管理员查看所有人的购物车
     * */
    @Override
    public Page<Order> adminGetAll(Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>(pageNo, pageSize);
        Page<Order> orderPage = orderMapper.selectPage(page, new QueryWrapper<Order>().isNotNull("book_id").isNotNull("user_id"));
        for (Order order : orderPage.getRecords()) {
            order.setBook(bookService.getById(order.getBookId()));
            order.setUser(userService.getById(order.getUserId()));
            order.setWord(wordService.getById(order.getWordId()));
        }
        return orderPage;
    }


    /*添加订单*/
    @Override
    public boolean addOrder(Order order) {
        return orderMapper.insert(order) > 0;
    }

    /*删除订单*/
    @Override
    public boolean deleteOrder(Order order) {
        wordService.deleteWord(orderMapper.selectById(order.getId()).getWordId());
        return orderMapper.deleteById(order.getId()) > 0;
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
    public boolean updateOrder(Order order) {
//      检库存是否充足
        Book book = bookMapper.selectOne(new QueryWrapper<Book>()
                .select("book_number")
                .eq("id", order.getBookId()));
        if (book.getBookNumber() < order.getBuyNumber()) {
            return false;
        }
//      更新书籍数量
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<Book>()
                .set(order.getBuyNumber() > 0, "book_number", book.getBookNumber() - order.getBuyNumber())
                .eq("id", order.getBookId());

//        更改订单中用户的信息,与扣款获取用户不同，这里是要用新的数据覆盖原来的用户数据
        User user = order.getUser();
        user.setId(order.getUserId());
        userService.updateUser(user);

//        扣款
        User userById = userService.getById(order.getUserId());
        double fare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)) + order.getExpressFare();
        if (userById.getUserMoney() - fare < 0) {
            return false;
        } else {
            userById.setUserMoney(userById.getUserMoney() - fare);
            userService.updateUser(userById);
        }

        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("buy_time", LocalDateTime.now())
                .set("order_status", OrderEnum.BUY)
                .set("comment_status", CommentEnum.COMMENTEDFORBIDDEN)
                .eq("id", order.getId());
        return (orderMapper.update(null, orderUpdateWrapper) > 0
                && bookMapper.update(null, bookUpdateWrapper) > 0);
    }

    /*更新订单(收货)
     * 设置书籍状态为已签收，
     * 设置签收时间。
     * 设置评论状态为可评论
     *
     * 商家收款，管理员收款  根据订单的价格，回扣，运费，折扣
     * */
    @Override
    public boolean confirmOrder(Order byId) {
//      由于前端只会发送orderId回来，所以需要根据这个id进行获取order的全部信息
        Order order =orderMapper.selectById(byId.getId());
//      设置管理员回扣
        double adminFare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)
                + order.getExpressFare()) * (order.getKickback() / 10);
        Long adminCount = adminMapper.selectCount(null);
        for (Admin admin : adminMapper.selectList(null)) {
            admin.setAdminMoney(admin.getAdminMoney() + adminFare / adminCount);
            if (!adminService.updateAdmin(admin)) {
                return false;
            }
        }

//      设置商家收益
        double shopkeeperFare = (order.getBuyNumber() * order.getBookPrice()
                * (order.getDiscount() / 10.0) + order.getExpressFare())
                - ((order.getBuyNumber() * order.getBookPrice()
                * (order.getDiscount() / 10.0) + order.getExpressFare()) * (order.getKickback() / 10));

        Book bookById = bookService.getById(order.getBookId());
        Shopkeeper spById = shopkeeperService.getById(bookById.getShopkeeperId());
        spById.setShopkeeperMoney(spById.getShopkeeperMoney() + shopkeeperFare);
        shopkeeperService.updateShopkeeper(spById);

        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("confirm_time", LocalDateTime.now())
                .set("order_status", OrderEnum.CONFIRM)
                .set("comment_status", CommentEnum.NOTCOMMENT)
                .eq("id", order.getId());
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
     *
     *
     * */
    @Override
    public boolean backOrder(Order byId) {
//        由于前端只会发送orderId回来，所以需要根据这个id进行获取order的全部信息
        Order order = getById(byId.getId());

//如果购买日期或者确认收货日期没有超过7天，可以退款，after是对的，测试收货日期加7天是否在今天以后判断是否超出7天无理由退货，
// 如果超出则退款，没超过今天则表明很久以前购买的了，不能退了
        if (order.getBuyTime().plusDays(7).isAfter(LocalDateTime.now())
                || order.getConfirmTime().plusDays(7).isAfter(LocalDateTime.now())) {
//            已经签收就扣除管理员，商户的收益，没有签收直接退还用户费用
            if (order.getOrderStatus() == OrderEnum.CONFIRM
                    || order.getOrderStatus().getStatus().equals(OrderEnum.CONFIRM.getStatus())) {
                //设置管理员回扣  退还
                double adminFare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)
                        + order.getExpressFare()) * (order.getKickback() / 10);
                Long adminCount = adminMapper.selectCount(null);
                for (Admin admin : adminMapper.selectList(null)) {
                    admin.setAdminMoney(admin.getAdminMoney() - adminFare / adminCount);
                    if (!adminService.updateAdmin(admin)) {
                        return false;
                    }
                }
                //设置商家收益  退还
                double shopkeeperFare = (order.getBuyNumber() * order.getBookPrice()
                        * (order.getDiscount() / 10.0) + order.getExpressFare())
                        - ((order.getBuyNumber() * order.getBookPrice()
                        * (order.getDiscount() / 10.0) + order.getExpressFare()) * (order.getKickback() / 10));

                Book bookById = bookService.getById(order.getBookId());
                Shopkeeper spById = shopkeeperService.getById(bookById.getShopkeeperId());
                spById.setShopkeeperMoney(spById.getShopkeeperMoney() - shopkeeperFare);
                shopkeeperService.updateShopkeeper(spById);
            }

            //退款
            User userById = userService.getById(order.getUserId());
            double fare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)) + order.getExpressFare();
            userById.setUserMoney(userById.getUserMoney() + fare);
            userService.updateUser(userById);


//        退款时退还购买书籍数量
            Book book = bookMapper.selectOne(new QueryWrapper<Book>().select("book_number").eq("id", order.getBookId()));
            UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<Book>()
                    .set(order.getBuyNumber() > 0, "book_number", book.getBookNumber() + order.getBuyNumber())
                    .eq("id", order.getBookId());

//      设置订单为退款
            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                    .set("back_time", LocalDateTime.now())
                    .set("order_status", OrderEnum.BACKING)
                    .set("comment_status", CommentEnum.COMMENTEDFORBIDDEN)
                    .eq("id", order.getId());
            return (orderMapper.update(null, orderUpdateWrapper) > 0 && bookMapper.update(null, bookUpdateWrapper) > 0);
        }else{
//            超过退货时间
            return false;
        }
    }

    @Override
    public boolean commentOrder(Order order) {
        Word word = order.getWord();
        wordMapper.insert(word);
//      设置订单为已评价
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("comment_status", CommentEnum.COMMENTED)
                .set("word_id", word.getId())
                .eq("order_id", order.getId());
        return orderMapper.update(null, orderUpdateWrapper) > 0;
    }

    @Override
    public boolean alterOrder(Order order) {
//        更改订单中用户的信息
        User user = order.getUser();
        user.setId(order.getUserId());
        userService.updateUser(user);

//      应用企业对订单的修改
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("book_price", order.getBookPrice())
                .set("discount", order.getDiscount())
                .set("express_fare",order.getExpressFare())
                .set("kickback",order.getKickback())
                .set("buy_number", order.getBuyNumber())
                .eq("id", order.getId());
        return orderMapper.update(null, orderUpdateWrapper) > 0;
    }


}
//测试全局回滚用的
//        try {
//            TimeUnit.SECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e+"超时了");
//        }