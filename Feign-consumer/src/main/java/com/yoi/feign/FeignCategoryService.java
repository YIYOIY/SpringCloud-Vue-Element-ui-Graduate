package com.yoi.feign;

import com.yoi.entity.Category;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.feignimpl.FeignCategoryServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@FeignClient(value = "category",fallback = FeignCategoryServiceImpl.class)
public interface FeignCategoryService {
    @GetMapping("/bookSeries")
    public ReturnInfo index(@RequestParam(value = "req",required = false) HttpServletRequest req, @RequestParam(value = "searchName",required = false)String searchName);
    @GetMapping("/series")
    public ReturnInfo getSeries();
    @PostMapping("/bookSeries")
    public ReturnInfo addBookSeries(@RequestBody Category Series);
    @DeleteMapping("/bookSeries")
    public ReturnInfo deleteBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId);
    @GetMapping("/bookSeriesBySeriesId")
    public ReturnInfo alterBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId);
    @PutMapping("/bookSeries")
    public ReturnInfo updateBookSeries(@RequestBody Category category);
}
