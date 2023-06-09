package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Repository
public interface BookMapper extends BaseMapper<Book> {
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

    @Select("select book_id,book_name,book_author, book_picture, book_factory, book_price,book_info,book_detail, book_addDate,series_name,book_num from t_book b,t_category c where b.series_id=c.series_id and b.book_name like  concat('%',#{keyword},'%')")
    List<Book> getAll(@Param("keyword") String keyword, @Param("page") Integer page);

    //        给导入导出的
    @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId ")
    List<Book> getAllByExcel();

    boolean addBook(@Param("book") Book book, Integer seriesId);

    @Delete("delete from t_book where bookId=#{bookId}")
    boolean deleteBook(Integer bookId);

    boolean updateBook(@Param("book") Book book, Integer seriesId);

    //        @Select("select count(*) from book where  bookName like '%${keyword}%'")
//        Long getCount(String keyword);
    @Select("select count(*) from t_book")
    Long getCount(String keyword);

    //        给导入导出的
    @Insert("insert into book(bookName) values (#{bookName})")
    long insertAll(String bookName);
}