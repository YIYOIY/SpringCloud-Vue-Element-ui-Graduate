package com.yoi.feign.service.feignimpl;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Series;
import com.yoi.feign.service.FeignSeriesService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignSeriesServiceImpl implements FeignSeriesService {
    @Override
    public ReturnInfo<PagePackage<Series>> index(@PathVariable("searchName") String searchName,
                                                 @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                 @Min(1) @PathVariable("pageSize") Integer pageSize) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Series> getSeries(@NotNull @PathVariable Long seriesId) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Series> addSeries(@RequestBody Series Series) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Series> deleteSeries(@Valid @RequestBody Series Series) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo<Series> updateSeries(@Valid @RequestBody Series series) {
        return new ReturnInfo<>(404, "后台服务超时或宕机，稍后再试！");
    }
}
