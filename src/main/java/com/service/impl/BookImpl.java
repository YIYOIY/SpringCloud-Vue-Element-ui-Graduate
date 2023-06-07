package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Book;
import com.mapper.BookMapper;
import com.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:38
 */
@Service
public class BookImpl extends ServiceImpl<BookMapper,Book>  implements BookService {
@Autowired
    private BookMapper bookMapper;

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public List<Book> getByType(String bookType) {
        return bookMapper.getByType(bookType);
    }

    @Override
    public Long checkBagData(Integer UserId) {
        return bookMapper.checkBagData(UserId);
    }

    @Override
    public List<Book> getAll(String keyword, Integer page) {
        return bookMapper.getAll(keyword,page);
    }

    @Override
    public boolean addBook(Book book) {
        int seriesId = bookMapper.getSeriesId(book.getSeriesName());
        return bookMapper.addBook(book,seriesId);
    }

    @Override
    public boolean deleteBook(Integer bookId) {
        return bookMapper.deleteBook(bookId);
    }

    @Override
    public boolean updateBook(Book book) {
        int seriesId = bookMapper.getSeriesId(book.getSeriesName());
        return bookMapper.updateBook(book,seriesId);
    }

    @Override
    public Long getCount(String keyword) {
        return bookMapper.getCount(keyword);
    }
}
