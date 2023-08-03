package com.yoi.feigncontroller;

import com.yoi.entity.Category;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.FeignCategoryService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
@RefreshScope
public class CategoryFeignController {
    @Resource
    private FeignCategoryService feignCategoryService;
    @GetMapping("/bookSeries")
    public ReturnInfo index(@RequestParam(value = "req",required = false) HttpServletRequest req, @RequestParam(value = "searchName",required = false)String searchName){
        return feignCategoryService.index(req, searchName);
    }
    @GetMapping("/series")
    public ReturnInfo getSeries(){
        return feignCategoryService.getSeries();
    }
    @PostMapping("/bookSeries")
    public ReturnInfo addBookSeries(@RequestBody Category Series){
        return feignCategoryService.addBookSeries(Series);
    }
    @DeleteMapping("/bookSeries")
    public ReturnInfo deleteBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId){
        return feignCategoryService.deleteBookSeries(seriesId);
    }
    @GetMapping("/bookSeriesBySeriesId")
    public ReturnInfo alterBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId){
        return feignCategoryService.alterBookSeries(seriesId);
    }

    @PutMapping("/bookSeries")
    public ReturnInfo updateBookSeries(@RequestBody Category category){
        return feignCategoryService.updateBookSeries(category);
    }
}
