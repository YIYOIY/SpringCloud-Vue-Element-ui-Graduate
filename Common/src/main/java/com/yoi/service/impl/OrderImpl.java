package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.*;
import com.yoi.enumvalue.CommentEnum;
import com.yoi.enumvalue.OrderEnum;
import com.yoi.mapper.*;
import com.yoi.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private SeriesMapper seriesMapper;
    @Resource
    private WordMapper wordMapper;
    @Resource
    private ImageMapper imageMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private ShopkeeperMapper shopkeeperMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Order> shopkeeperGetAll(Long shopkeeperId, Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>(pageNo, pageSize);
        List<Long> orders = new ArrayList<>();

//        查询出所有未支付的订单，此行为是为了获取订单的总数量
        List<Order> allOrders = orderMapper.selectList(new QueryWrapper<Order>().eq("order_status", OrderEnum.NOTPAIED));

        for (Order record : allOrders) {
//            根据订单的书籍id和前端传递的企业id在book中查询订单中该企业的下的书籍
            Book book = bookMapper.selectOne(new QueryWrapper<Book>().eq("id", record.getBookId()).eq("shopkeeper_id", shopkeeperId));
//            如果书籍不为空，则表示该企业有订单
            if (book != null) {
                orders.add(record.getId());
            }
        }

//        根据有效的订单id在订单中重新分页查询
        Page<Order> shopkeeperOrder = orderMapper.selectPage(page, new QueryWrapper<Order>().in("id", orders));
//       将企业的订单嵌入数据
        return getOrderPage(shopkeeperOrder);
    }

    /*
     * 管理员查看所有人的购物车
     * */
    @Override
    public Page<Order> adminGetAll(Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>(pageNo, pageSize);
        Page<Order> orderPage = orderMapper.selectPage(page, new QueryWrapper<Order>().isNotNull("book_id").isNotNull("user_id"));
        return getOrderPage(orderPage);
    }

    /*
     * 用户查看自己的购物车
     * */
    @Override
    public Page<Order> userGetAll(Long userId, Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>(pageNo, pageSize);
        Page<Order> orderPage = orderMapper.selectPage(page, new QueryWrapper<Order>().eq("user_id", userId));
        return getOrderPage(orderPage);
    }

    /*
     * 通过order表的id将购买的信息一同放入order实体类中
     * */
    @Override
    public Order getById(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        setBookInformation(order);
        return order;
    }

    private Page<Order> getOrderPage(Page<Order> orderPage) {
        for (Order record : orderPage.getRecords()) {
            setBookInformation(record);
        }
        return orderPage;
    }

    private void setBookInformation(Order order) {
        Book book = bookMapper.selectById(order.getBookId());
        if (!ObjectUtils.isEmpty(imageMapper.selectById(book.getImageId())))
            book.setImage(imageMapper.selectById(book.getImageId()));
        if (!ObjectUtils.isEmpty(wordMapper.selectById(book.getWordId())))
            book.setWord(wordMapper.selectById(book.getWordId()));
        if (!ObjectUtils.isEmpty(shopkeeperMapper.selectById(book.getShopkeeperId())))
            book.setShopkeeper(shopkeeperMapper.selectById(book.getShopkeeperId()));
        if (!ObjectUtils.isEmpty(seriesMapper.selectById(book.getSeriesId())))
            book.setSeries(seriesMapper.selectById(book.getSeriesId()));
        order.setBook(book);

        User user = userMapper.selectById(order.getUserId());
        if (!ObjectUtils.isEmpty(imageMapper.selectById(user.getImageId()))) {
            user.setImage(imageMapper.selectById(user.getImageId()));
        }
        order.setUser(user);
        if (!ObjectUtils.isEmpty(wordMapper.selectById(order.getWordId())))
            order.setWord(wordMapper.selectById(order.getWordId()));
    }


    /*添加订单*/
    @Override
    public boolean addOrder(Order order) {
        return orderMapper.insert(order) > 0;
    }

    /**
     * 删除订单
     * 如果订单是用户已经购买未签收，就用户的钱退回，将购买的书籍数量还原
     * 无论是管理员删除订单还是用户自己的删除订单，还是商家删除书籍导致的删除订单（商家的订单页面只能看见未支付的）
     */
    @Override
    public boolean deleteOrder(Order order) {
        if (order.getOrderStatus() == OrderEnum.BUY
                || order.getOrderStatus().getStatus().equals(OrderEnum.BUY.getStatus())) {
            //退款
            User userById = userMapper.selectById(order.getUserId());
            double fare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0))
                    + order.getExpressFare();
            userById.setUserMoney(userById.getUserMoney() + fare);
            userMapper.updateById(userById);

            Book book = bookMapper.selectOne(new QueryWrapper<Book>()
                    .select("book_number")
                    .eq("id", order.getBookId()));

            //      更新书籍数量
            UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<Book>()
                    .set(order.getBuyNumber() > 0, "book_number", book.getBookNumber() + order.getBuyNumber())
                    .eq("id", order.getBookId());
            bookMapper.update(null, bookUpdateWrapper);
        }

        if (!ObjectUtils.isEmpty(order.getWordId())) {
            wordMapper.deleteById(order.getWordId());
        }

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
        userMapper.updateById(user);

//        扣款
        User userById = userMapper.selectById(order.getUserId());
        double fare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)) + order.getExpressFare();
        if (userById.getUserMoney() - fare < 0) {
            return false;
        } else {
            userById.setUserMoney(userById.getUserMoney() - fare);
            userMapper.updateById(userById);
        }

        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("buy_time", LocalDateTime.now())
                .set("order_status", OrderEnum.BUY)
                .set("comment_status", CommentEnum.COMMENTEDFORBIDDEN)
                .eq("id", order.getId());
//测试全局回滚用的
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e+"超时了");
        }
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
        Order order = orderMapper.selectById(byId.getId());
//      设置管理员回扣
        double adminFare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)
                + order.getExpressFare()) * (order.getKickback() / 10);
        if (adminMapper.selectCount(null) > 0) {
            for (Admin admin : adminMapper.selectList(null)) {
                admin.setAdminMoney(admin.getAdminMoney() + adminFare / adminMapper.selectCount(null));
                adminMapper.updateById(admin);
            }
        }
//      设置商家收益
        double shopkeeperFare = (order.getBuyNumber() * order.getBookPrice()
                * (order.getDiscount() / 10.0) + order.getExpressFare())
                - ((order.getBuyNumber() * order.getBookPrice()
                * (order.getDiscount() / 10.0) + order.getExpressFare()) * (order.getKickback() / 10));

        Book bookById = bookMapper.selectById(order.getBookId());
        Shopkeeper spById = shopkeeperMapper.selectById(bookById.getShopkeeperId());
        spById.setShopkeeperMoney(spById.getShopkeeperMoney() + shopkeeperFare);
        System.out.println(spById);
        shopkeeperMapper.updateById(spById);

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
                if (adminMapper.selectCount(null) > 0) {
                    for (Admin admin : adminMapper.selectList(null)) {
                        admin.setAdminMoney(admin.getAdminMoney() - adminFare / adminMapper.selectCount(null));
                        adminMapper.updateById(admin);
                    }
                }
                //设置商家收益  退还
                double shopkeeperFare = (order.getBuyNumber() * order.getBookPrice()
                        * (order.getDiscount() / 10.0) + order.getExpressFare())
                        - ((order.getBuyNumber() * order.getBookPrice()
                        * (order.getDiscount() / 10.0) + order.getExpressFare()) * (order.getKickback() / 10));

                Book bookById = bookMapper.selectById(order.getBookId());
                Shopkeeper spById = shopkeeperMapper.selectById(bookById.getShopkeeperId());
                spById.setShopkeeperMoney(spById.getShopkeeperMoney() - shopkeeperFare);
                shopkeeperMapper.updateById(spById);
            }

            //退款
            User userById = userMapper.selectById(order.getUserId());
            double fare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0)) + order.getExpressFare();
            userById.setUserMoney(userById.getUserMoney() + fare);
            userMapper.updateById(userById);


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
        } else {
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
        userMapper.updateById(user);

//      应用企业对订单的修改
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>()
                .set("book_price", order.getBookPrice())
                .set("discount", order.getDiscount())
                .set("express_fare", order.getExpressFare())
                .set("kickback", order.getKickback())
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