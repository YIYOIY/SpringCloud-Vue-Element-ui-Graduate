package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Repository
public interface BookMapper extends BaseMapper<Book>{
        @Select("select seriesId as bookNo from bookseries  where seriesName=#{seriesName}")
        Integer getSeriesId(String seriesName);

        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and bookId=#{bookId}")
        Book getById(@Param("bookId") Integer id);

        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and seriesName=#{boookType}")
        List<Book> getByType(String bookType);

        @Select("select count(*) from book o,bag b where b.bookId=o.bookId and o.bookId=#{bookId}")
        Long checkBagData(Integer bookId);

//        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and b.bookName like %${keyword}% limit #{page},5")
//        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and b.bookName like '%${keyword}%' limit #{page},5")
        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and b.bookName like '%${keyword}%'")
        List<Book> getAll(@Param("keyword") String keyword, @Param("page") Integer page);

        boolean addBook(@Param("book") Book book,Integer seriesId);

        @Delete("delete from book where bookId=#{bookId}")
        boolean deleteBook(Integer bookId);

        boolean updateBook(@Param("book") Book book,Integer seriesId);

        @Select("select count(*) from book where  bookName like '%${keyword}%'")
        Long getCount(String keyword);
}
