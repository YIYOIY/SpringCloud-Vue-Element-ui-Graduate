package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Series;
import com.yoi.mapper.SeriesMapper;
import com.yoi.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
//使用lombok注解自动注入
@RequiredArgsConstructor
public class SeriesImpl extends ServiceImpl<SeriesMapper, Series> implements SeriesService {
    private final SeriesMapper seriesMapper;

    @Override
    public Page<Series> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<Series> seriesPage = new Page<>(pageNo, pageSize);
        return seriesMapper.selectPage(seriesPage, new QueryWrapper<Series>().like("series_id", keyword));
    }

    @Override
    public Series getSeries(Long bookSeriesId) {
        return seriesMapper.selectById(bookSeriesId);
    }

    @Override
    public boolean addSeries(Series series) {
        return seriesMapper.insert(series) > 0;
    }

    @Override
    public boolean deleteSeries(Long seriesId) {
        return seriesMapper.deleteById(seriesId) > 0;
    }

    @Override
    public boolean updateSeries(Series series) {
        return seriesMapper.updateById(series) > 0;
    }

}
