package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.*;
import com.yoi.enumvalue.OrderEnum;
import com.yoi.mapper.*;
import com.yoi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author 游弋
 * @create 2023-03-31 10:38
 */
@Service
@RequiredArgsConstructor
public class BookImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    private final BookMapper bookMapper;
    private final SeriesMapper seriesMapper;
    private final ImageMapper imageMapper;
    private final WordMapper wordMapper;
    private final ShopkeeperMapper shopkeeperMapper;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Override
    public Book getById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        Image image = imageMapper.selectById(book.getImageId());
        book.setImage(image);
        Word word = wordMapper.selectById(book.getWordId());
        book.setWord(word);
        Shopkeeper shopkeeper = shopkeeperMapper.selectById(book.getShopkeeperId());
        book.setShopkeeper(shopkeeper);
        Series series = seriesMapper.selectById(book.getSeriesId());
        book.setSeries(series);
        return book;
    }

    //    管理员获取所有书籍，商城首页
    @Override
    public Page<Book> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<Book> Page = new Page<>(pageNo, pageSize);
        Page<Book> bookPage = bookMapper.selectPage(Page, new QueryWrapper<Book>().like("book_name", keyword));
        for (Book record : bookPage.getRecords()) {
            Image image = imageMapper.selectById(record.getImageId());
            record.setImage(image);
            Word word = wordMapper.selectById(record.getWordId());
            record.setWord(word);
            Shopkeeper shopkeeper = shopkeeperMapper.selectById(record.getShopkeeperId());
            record.setShopkeeper(shopkeeper);
            Series series = seriesMapper.selectById(record.getSeriesId());
            record.setSeries(series);
        }
        return bookPage;
    }

    //    企业获取企业所发布的书籍
    @Override
    public Page<Book> getShopkeeperAll(String keyword, Integer pageNo, Integer pageSize, Long shopkeeperId) {
        Page<Book> Page = new Page<>(pageNo, pageSize);
        Page<Book> bookPage = bookMapper.selectPage(Page, new QueryWrapper<Book>().like("book_name", keyword).eq("shopkeeper_id", shopkeeperId));
        for (Book record : bookPage.getRecords()) {
            Image image = imageMapper.selectById(record.getImageId());
            record.setImage(image);
            Word word = wordMapper.selectById(record.getWordId());
            record.setWord(word);
            Shopkeeper shopkeeper = shopkeeperMapper.selectById(record.getShopkeeperId());
            record.setShopkeeper(shopkeeper);
            Series series = seriesMapper.selectById(record.getSeriesId());
            record.setSeries(series);
        }
        return bookPage;
    }

    @Override
    public Page<Book> selectBySeries(String seriesName, Integer pageNo, Integer pageSize) {
        Page<Book> Page = new Page<>(pageNo, pageSize);

        Series series = seriesMapper.selectOne(new QueryWrapper<Series>().select("*").eq("series_name", seriesName));
//获取分页后的书籍系列
        Page<Book> bookPage = bookMapper.selectPage(Page, new QueryWrapper<Book>().eq("series_id", series.getId()));
//        为书籍系列的每本书设置系列名,商家,壁纸,介绍
        for (Book record : bookPage.getRecords()) {
            Image image = imageMapper.selectById(record.getImageId());
            record.setImage(image);
            Word word = wordMapper.selectById(record.getWordId());
            record.setWord(word);
            Shopkeeper shopkeeper = shopkeeperMapper.selectById(record.getShopkeeperId());
            record.setShopkeeper(shopkeeper);
            record.setSeries(series);
        }
        return bookPage;
    }

    @Override
    public Boolean addBook(Book book) {
        if (!ObjectUtils.isEmpty(book.getImage())) {
            String picture = book.getImage().getPicture();
            Image image = new Image(null, picture, null, null, null);
            if (imageMapper.insert(image) < 0) {
                return false;
            } else {
                book.setImageId(image.getId());
            }
        }
        if (!ObjectUtils.isEmpty(book.getWord())) {
            Word word = new Word(null, book.getWord().getBookDescribe(), book.getWord().getBookDetail(),
                    null, null, null, null);
            if (wordMapper.insert(word) < 0) {
                return false;
            } else {
                book.setWordId(word.getId());
            }
        }
        return bookMapper.insert(book) > 0;
    }

    @Override
    public Boolean deleteBook(Book book) {
        for (Order order : orderMapper.selectList(new QueryWrapper<Order>().eq("book_id", book.getId()))) {
            if (order.getOrderStatus() == OrderEnum.BUY
                    || order.getOrderStatus().getStatus().equals(OrderEnum.BUY.getStatus())) {
                //退款
                User userById = userMapper.selectById(order.getUserId());
                double fare = (order.getBuyNumber() * order.getBookPrice() * (order.getDiscount() / 10.0))
                        + order.getExpressFare();
                userById.setUserMoney(userById.getUserMoney() + fare);
                userMapper.updateById(userById);
                if (!ObjectUtils.isEmpty(order.getWordId())) {
                    wordMapper.deleteById(order.getWordId());
                }
                orderMapper.deleteById(order.getId());
            }else {
//                耽误了一个小时，忘记了写订单不是已支付直接删除订单
                orderMapper.deleteById(order.getId());
            }
        }
        wordMapper.deleteById(book.getWordId());
        imageMapper.deleteById(book.getImageId());
        return bookMapper.deleteById(book.getId()) > 0;
    }

    @Override
    public Boolean updateBook(Book book) {
        if (!ObjectUtils.isEmpty(book.getImage())) {
//            如果用户创建时没有上传头像，更新时检测到上传就创建一个图像
            if (book.getImageId() == null) {
                Image image = new Image(null, book.getImage().getPicture(),
                        null, null, null);
                if (imageMapper.insert(image) > 0) {
                    book.setImageId(image.getId());
                } else {
                    return false;
                }
            } else {
//                用户创建时已经上传头像，更新时只需要更新头像
                Image image = new Image(book.getImageId(), book.getImage().getPicture(),
                        null, null, null);
                if (imageMapper.updateById(image) < 0) {
                    return false;
                }
            }
        }
        if (!ObjectUtils.isEmpty(book.getWord())) {
//            如果用户创建时没有上传描述，更新时检测到上传就创建一个描述对象
            if (book.getWordId() == null) {
                Word word = new Word(null, book.getWord().getBookDescribe(), book.getWord().getBookDetail(),
                        null, null, null, null);
                if (wordMapper.insert(word) > 0) {
                    book.setImageId(word.getId());
                } else {
                    return false;
                }
            } else {
//                用户创建时已经上传书籍描述，更新时只需要更新书籍描述
                Word word = new Word(book.getWordId(), book.getWord().getBookDescribe(), book.getWord().getBookDetail(),
                        null, null, null, null);
                if (wordMapper.updateById(word) < 0) {
                    return false;
                }
            }
        }
        return bookMapper.updateById(book) > 0;
    }


}
