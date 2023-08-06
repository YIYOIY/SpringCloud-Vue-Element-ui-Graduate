package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Book;
import com.yoi.entity.Series;
import com.yoi.mapper.BookMapper;
import com.yoi.mapper.SeriesMapper;
import com.yoi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:38
 */
@Service
public class BookImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    private final BookMapper bookMapper;
    private final SeriesMapper seriesMapper;

    //    构造器注入
    @Autowired
    public BookImpl(BookMapper bookMapper, SeriesMapper seriesMapper) {
        this.bookMapper = bookMapper;
        this.seriesMapper = seriesMapper;
    }

    @Override
    public Book getById(Long bookId) {
        return bookMapper.selectById(bookId);
    }

    @Override
    public Page<Book> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<Book> Page = new Page<>(pageNo, pageSize);
        return bookMapper.selectPage(Page, new QueryWrapper<Book>().like("book_name", keyword));
    }

    @Override
    public Page<Book> selectBySeries(String seriesName, Integer pageNo, Integer pageSize) {
        Page<Book> Page = new Page<>(pageNo, pageSize);

        Series series = seriesMapper.selectOne(new QueryWrapper<Series>().select("series_id").eq("series_name", seriesName));
//获取分页后的书籍系列
        Page<Book> bookPage = bookMapper.selectPage(Page, new QueryWrapper<Book>().eq("series_id", series.getId()));
//        为书籍系列的每本书设置系列名
        List<Book> books = bookPage.getRecords();
        for (Book book : books) {
            book.setSeries(series);
        }
        bookPage.setRecords(books);
        return bookPage;
    }

    @Override
    public Boolean addBook(Book book) {
        return bookMapper.insert(book) > 0;
    }

    @Override
    public Boolean deleteBook(Long bookId) {
        return bookMapper.deleteById(bookId) > 0;
    }

    @Override
    public Boolean updateBook(Book book) {
        return bookMapper.updateById(book) > 0;
    }


}
