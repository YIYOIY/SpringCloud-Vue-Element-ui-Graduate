package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Category;
import com.mapper.CategoryMapper;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class CategoryImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<Category> getAll(String keyword) {
        List<Category> categories = categoryMapper.selectList(null);
        return categories;
    }
    @Override
    public Category getBySeriesId(Integer bookSeriesId) {
        return categoryMapper.selectById(bookSeriesId);
    }

//    @Override
//    public Long checkBagData(int bookSeriesId) {
//        return categoryMapper.checkBagData(bookSeriesId);
//    }

    @Override
    public boolean addBookSeries(Category category) {
        return categoryMapper.insert(category)>0;
    }

    @Override
    public boolean deleteBookSeries(Integer seriesId) {
        return categoryMapper.deleteById(seriesId)>0;
    }

    @Override
    public boolean updateBookSeries(Category category) {
        return categoryMapper.updateById(category)>0;
    }

    @Override
    public List<Category> getSeriesName() {
        List<Category> categories = categoryMapper.selectList(null);
        return categories;
    }
}
