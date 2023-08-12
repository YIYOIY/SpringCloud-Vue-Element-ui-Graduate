package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Series;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.SeriesService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RefreshScope
@Validated
@RequiredArgsConstructor
public class SeriesController {
    private final SeriesService seriesService;

    @CrossOrigin
    @GetMapping("/series/{searchName}/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Series>> index(@PathVariable("searchName") String searchName,
                                                 @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                 @Min(1) @PathVariable("pageSize") Integer pageSize) {
        if (searchName.equals("null") || StringUtils.isEmpty(searchName)) {
            searchName = "";
        }
        Page<Series> all = seriesService.getAll(searchName, pageNo, pageSize);
        if (!all.getRecords().isEmpty()) {
            PagePackage<Series> seriesPagePackage = new PagePackage<>(all.getRecords(), all.getCurrent(), all.getPages(), all.getSize(), all.getTotal(), all.hasNext(), all.hasPrevious());
            return new ReturnInfo<>(200, "查询成功！", seriesPagePackage);
        } else {
            return new ReturnInfo<>(404, "查询失败，无此数据！");
        }
    }

    @GetMapping("/series/{seriesId}")
    public ReturnInfo<Series> getSeries(@NotNull @PathVariable("seriesId") Long seriesId) {
        Series series = seriesService.getSeries(seriesId);
        return new ReturnInfo<>(200, "查询成功！", series);
    }

    @PostMapping("/series")
    public ReturnInfo<Series> addSeries(@RequestBody Series Series) {
        if (seriesService.addSeries(Series)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }
    }
    @GlobalTransactional(rollbackFor = Exception.class)
    @DeleteMapping("/series")
    public ReturnInfo<Series> deleteSeries(@Valid @RequestBody Series Series) {
        if (seriesService.deleteSeries(Series)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }
    }

    @PutMapping("/series")
    public ReturnInfo<Series> updateSeries(@Valid @RequestBody Series series) {
        if (seriesService.updateSeries(series)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }
    }
}
