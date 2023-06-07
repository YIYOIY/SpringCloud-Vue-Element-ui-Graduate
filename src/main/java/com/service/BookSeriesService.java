package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.BookSeries;

import java.sql.Connection;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:20 PM
 */
public interface BookSeriesService extends IService<BookSeries> {
    List<BookSeries> getAll(String keyword);
    BookSeries getBySeriesId(int bookSeriesId);
    Long checkBagData(int bookSeriesId);
    boolean addBookSeries(BookSeries bookSeries);
    boolean deleteBookSeries(Integer bookSeries);
    boolean updateBookSeries(BookSeries bookSeries);

}
