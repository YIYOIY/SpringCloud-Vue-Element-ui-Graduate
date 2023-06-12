package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Book;
import com.entity.Category;
import com.entity.Order;
import com.mapper.BookMapper;
import com.mapper.CategoryMapper;
import com.mapper.OrderMapper;
import com.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:38
 */
@Service
public class BookImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

//    @Override
//    public List<Book> getByType(String bookType) {
//        return bookMapper.getByType(bookType);
//    }

//    @Override
//    public Long checkBagData(Integer UserId) {
//        return bookMapper.checkBagData(UserId);
//    }

    @Override
    public List<Book> getAll(String keyword, Integer page) {
        return bookMapper.getAll(keyword, page);
    }

    @Override
    public List<Book> getAllForExcel() {
        return bookMapper.getAllForExcel();
    }

    @Override
    public boolean addBook(Book book) {
        QueryWrapper<Category> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.select("series_id").and(i -> i.eq("series_name", book.getSeriesName()));
        Category category = categoryMapper.selectOne(QueryWrapper);
        return bookMapper.addBook(book,category.getSeriesId());
    }

    @Override
    public boolean deleteBook(Integer bookId) {
        return bookMapper.deleteBook(bookId);
    }


    @Override
    public boolean updateBook(Book book) {
        QueryWrapper<Category> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.select("series_id").and(i -> i.eq("series_name", book.getSeriesName()));
        Category category = categoryMapper.selectOne(QueryWrapper);

        return bookMapper.updateBook(book,category.getSeriesId());
    }

    @Override
    public Long getCount(String keyword) {
        return bookMapper.getCount(keyword);
    }

    @Override
    public List<Book> selectBySeries(String seriesName) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Category> eq = categoryQueryWrapper.select("series_id").eq("series_name", seriesName);
        Category category = categoryMapper.selectOne(eq);

        HashMap bookHashMap = new HashMap<String,Integer>();
        bookHashMap.put("series_id",category.getSeriesId());
        return bookMapper.selectByMap(bookHashMap);
    }
}
