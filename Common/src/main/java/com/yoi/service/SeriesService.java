package com.yoi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Series;

/**
 * @author 游弋
 * @create 2023-04-02 7:20 PM
 */
public interface SeriesService extends IService<Series> {
    Page<Series> getAll(String keyword, Integer pageNo, Integer pageSize);
    Series getSeries(Long bookSeriesId);
    boolean addSeries(Series series);
    boolean deleteSeries(Series series);
    boolean updateSeries(Series series);

}
