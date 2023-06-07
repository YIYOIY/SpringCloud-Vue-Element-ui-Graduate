package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Bag;
import com.entity.Book;
import com.service.BookService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Repository
public interface BagMapper extends BaseMapper<Bag>{
        @Select("select v.userId,b.bookId,a.bagId,b.bookName,b.bookPicture,b.bookPrice,a.bookNumber,a.addTime from book as b,bag as a,user as v where a.bookId=b.bookId and a.userId=v.userId and v.userId=#{userId} order by bagId asc limit #{pageStart},5")
        List<Bag> userGetAll(Integer userId, Integer pageStart);

        @Select("select bagId,bookNumber,addTime,bookName,bookPicture,bookPrice,userName from book,bag,user where bag.bookId=book.bookId and bag.userId=user.userId limit #{pageStart},5 ")
        List<Bag> adminGetAll(Integer pageStart);

        @Insert("insert into bag(bookId, userId, bookNumber) values(#{bookId},#{userId},#{bookNumber})")
        public boolean addBag(Bag bag) ;

        @Delete("delete from bag where bagId=#{bagId}")
        public boolean deleteBag(Integer bagId) ;

        @Select("select count(*) from bag")
        public Long adminGetCount();

        @Select("select count(*) from bag where bag.userId=#{userId}")
        public Long userGetCount(Integer userId) ;
}
