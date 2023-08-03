package com.yoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoi.entity.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("select book_id,book_name,book_author, book_picture, book_factory, book_price,book_info,book_detail, book_addDate,series_name,book_num from t_book b,t_category c where b.series_id=c.series_id and b.book_id=#{bookId}")
    Book getById(@Param("bookId") Integer id);


//    @Select("select count(*) from book o,bag b where b.bookId=o.bookId and o.bookId=#{bookId}")
//    Long checkBagData(Integer bookId);

//        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and b.bookName like %${keyword}% limit #{page},5")
//        @Select("select bookId,bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookAddDate, seriesName from book b,bookseries s where b.bookseries=s.seriesId and b.bookName like '%${keyword}%' limit #{page},5")

    @Select("select book_id,book_name,book_author, book_picture, book_factory, book_price,book_info,book_detail, book_addDate,series_name,book_num from t_book b,t_category c where b.series_id=c.series_id and b.book_name like  concat('%',#{keyword},'%')")
    List<Book> getAll(@Param("keyword") String keyword, @Param("page") Integer page);

    boolean addBook(@Param("book") Book book, @Param("seriesId") Integer seriesId);

    @Delete("delete from t_book where book_id=#{bookId}")
    boolean deleteBook(Integer bookId);

    boolean updateBook(@Param("book") Book book,@Param("seriesId")Integer seriesId);

//        @Select("select count(*) from book where  bookName like '%${keyword}%'")
//        Long getCount(String keyword);
    @Select("select count(*) from t_book")
    Long getCount(String keyword);

    //        给导入导出的测试
    @Insert("insert into t_book(book_name) values (#{bookName})")
    long insertAll(String bookName);

    List<Book> getAllForExcel();
}