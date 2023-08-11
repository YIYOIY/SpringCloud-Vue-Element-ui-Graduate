package com.yoi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Book;

public interface BookService extends IService<Book> {
    Page<Book> getAll(String keyword, Integer pageNo, Integer pageSize);

    Page<Book> getShopkeeperAll(String keyword, Integer pageNo, Integer pageSize, Long shopkeeperId);

    Page<Book> selectBySeries(String seriesName, Integer pageNo, Integer pageSize);

    //        Long getCount(String keyword);
    Boolean addBook(Book Book);

    Boolean deleteBook(Book book);

    Book getById(Long bookId);

    Boolean updateBook(Book Book);
}
