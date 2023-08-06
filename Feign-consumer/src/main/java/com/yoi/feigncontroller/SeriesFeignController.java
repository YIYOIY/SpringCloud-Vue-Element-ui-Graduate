package com.yoi.feigncontroller;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Series;
import com.yoi.feign.service.FeignSeriesService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
@RefreshScope
@Validated
public class SeriesFeignController {
    @Resource
    private FeignSeriesService feignSeriesService;
    @CrossOrigin
    @GetMapping("/series/{searchName}/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Series>> index(@PathVariable("searchName") String searchName,
                                                 @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                 @Min(1) @PathVariable("pageSize") Integer pageSize) {
        return feignSeriesService.index(searchName,pageNo,pageSize);
    }
    @GetMapping("/series/{seriesId}")
    public ReturnInfo<Series> getSeries(@NotNull @PathVariable("seriesId") Long seriesId) {
        return feignSeriesService.getSeries(seriesId);
    }
    @PostMapping("/series")
    public ReturnInfo<Series> addSeries(@RequestBody Series Series) {
        return feignSeriesService.addSeries(Series);
    }
    @DeleteMapping("/series")
    public ReturnInfo<Series> deleteSeries(@Valid @RequestBody Series Series) {
        return feignSeriesService.deleteSeries(Series);
    }
    @PutMapping("/series")
    public ReturnInfo<Series> updateSeries(@Valid @RequestBody Series series) {
        return feignSeriesService.updateSeries(series);
    }
}
