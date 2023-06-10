package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
//    @Select(" select * from bookseries where seriesName like '%${keyword}%' ")
//    public List<Category> getAll(String keyword);

//    @Select("select * from bookseries where seriesId=#{seriesId}")
//    public Category getById(Integer seriesId);

//    @Select("select count(*) from book b,bookSeries s where b.bookSeries=s.seriesId and s.seriesId=#{seriesId}")
//    public Long checkBagData(Integer seriesId);

//    @Insert("insert into bookseries(seriesName) values(#{seriesName})")
//    public boolean addBookSeries(Category category);

//    @Delete("delete from bookseries where seriesId=#{bookSeriesId}")
//    public boolean deleteBookSeries(Integer bookSeriesId);

//    @Update("update bookseries set seriesName=#{seriesName} where seriesId=#{seriesId}")
//    public boolean updateBookSeries(Category category);
}
