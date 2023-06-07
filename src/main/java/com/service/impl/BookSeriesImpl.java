package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.BookSeries;
import com.mapper.BookSeriesMapper;
import com.service.BookSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class BookSeriesImpl extends ServiceImpl<BookSeriesMapper, BookSeries> implements BookSeriesService {
    @Autowired
    BookSeriesMapper bookSeriesMapper;
    @Override
    public List<BookSeries> getAll(String keyword) {
        return bookSeriesMapper.getAll(keyword);
    }

    @Override
    public BookSeries getBySeriesId(int bookSeriesId) {
        return bookSeriesMapper.getById(bookSeriesId);
    }

    @Override
    public Long checkBagData(int bookSeriesId) {
        return bookSeriesMapper.checkBagData(bookSeriesId);
    }

    @Override
    public boolean addBookSeries(BookSeries bookSeries) {
        return bookSeriesMapper.addBookSeries(bookSeries);
    }

    @Override
    public boolean deleteBookSeries(Integer bookSeries) {
        return bookSeriesMapper.deleteBookSeries(bookSeries);
    }

    @Override
    public boolean updateBookSeries(BookSeries bookSeries) {
        return bookSeriesMapper.updateBookSeries(bookSeries);
    }
}
