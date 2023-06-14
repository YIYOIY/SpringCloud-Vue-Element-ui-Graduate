package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Repository
public interface OrderMapper extends BaseMapper<Order>{
//        @Select("select v.userId,b.bookId,a.bagId,b.bookName,b.bookPicture,b.bookPrice,a.bookNumber,a.addTime from book as b,bag as a,user as v where a.bookId=b.bookId and a.userId=v.userId and v.userId=#{userId} order by bagId asc limit #{pageStart},5")
        List<Order> userGetAll(Integer userId, Integer pageStart);

//        @Select("select bagId,bookNumber,addTime,bookName,bookPicture,bookPrice,userName from book,bag,user where bag.bookId=book.bookId and bag.userId=user.userId limit #{pageStart},5 ")
        List<Order> adminGetAll(Integer pageStart);

        Order getId(Integer orderId);

//        public boolean addBag(Order order) ;

//        public boolean deleteBag(Integer bagId) ;

//        public Long adminGetCount();

//        public Long userGetCount(Integer userId) ;
}
