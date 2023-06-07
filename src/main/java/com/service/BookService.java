package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Book;

import java.util.List;

public interface BookService extends IService<Book> {
        Book getById(Integer id);

        List<Book> getByType(String bookType);
        Long checkBagData(Integer UserId);
        List<Book> getAll(String keyword, Integer page);
        boolean addBook(Book Book);
        boolean deleteBook(Integer bookId);
        boolean updateBook(Book Book);
        Long getCount(String keyword);
}
