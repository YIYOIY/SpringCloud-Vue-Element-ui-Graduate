package com.yoi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Book;

import java.util.List;

public interface BookService extends IService<Book> {
        Book getById(Integer id);
        List<Book> getAll(String keyword, Integer page);
        List<Book> getAllForExcel();
        boolean addBook(Book Book);
        boolean deleteBook(Integer bookId);
        boolean updateBook(Book Book);
        Long getCount(String keyword);

        List<Book> selectBySeries(String seriesName);
}
