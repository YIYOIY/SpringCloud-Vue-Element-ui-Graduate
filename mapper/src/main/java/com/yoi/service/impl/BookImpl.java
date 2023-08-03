package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Book;
import com.yoi.entity.Category;
import com.yoi.mapper.BookMapper;
import com.yoi.mapper.CategoryMapper;
import com.yoi.service.BookService;
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
    private CategoryMapper categoryMapper;


    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

//    @Override
//    public List<Book> getByType(String bookType) {
//        return bookMapper.getByType(bookType);
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
        HashMap bookHashMap = new HashMap<String,Object>();
        bookHashMap.put("series_id",category.getSeriesId());
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Book> seriesId = bookQueryWrapper.select("*").eq("series_id", category.getSeriesId());
        List<Book> books = bookMapper.selectList(seriesId);
        for (Book book : books) {
            book.setSeriesName(seriesName);
        }
        return books;
    }
}
