package com.yoi.feign.service;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Series;
import com.yoi.feign.service.feignimpl.FeignSeriesServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@Validated
@FeignClient(value = "series", fallback = FeignSeriesServiceImpl.class)
public interface FeignSeriesService {
    @CrossOrigin
    @GetMapping("/series/{searchName}/{pageNo}/{pageSize}")
    ReturnInfo<PagePackage<Series>> index(@PathVariable("searchName") String searchName,
                                          @Min(1) @PathVariable("pageNo") Integer pageNo,
                                          @Min(1) @PathVariable("pageSize") Integer pageSize);

    @GetMapping("/series/{seriesId}")
    ReturnInfo<Series> getSeries(@NotNull @PathVariable("seriesId") Long seriesId);

    @PostMapping("/series")
    ReturnInfo<Series> addSeries(@RequestBody Series Series);

    @DeleteMapping("/series")
    ReturnInfo<Series> deleteSeries(@Valid @RequestBody Series Series);

    @PutMapping("/series")
    ReturnInfo<Series> updateSeries(@Valid @RequestBody Series series);
}
