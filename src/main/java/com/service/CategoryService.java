package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Category;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:20 PM
 */
public interface CategoryService extends IService<Category> {
    List<Category> getAll(String keyword);
    Category getBySeriesId(Integer bookSeriesId);
//    Long checkBagData(int bookSeriesId);
    boolean addBookSeries(Category category);
    boolean deleteBookSeries(Integer bookSeries);
    boolean updateBookSeries(Category category);

}
