package com.yoi.feigncontroller;

import com.yoi.entity.Category;
import com.yoi.feign.FeignCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
public class CategoryFeignController {
    @Resource
    private FeignCategoryService feignCategoryService;
    @GetMapping("/bookSeries")
    public List<Category> index( @RequestParam(value = "req",required = false) HttpServletRequest req, @RequestParam(value = "searchName",required = false)String searchName){
        return feignCategoryService.index(req, searchName);
    }
    @GetMapping("/series")
    public List<Category> getSeries(){
        return feignCategoryService.getSeries();
    }
    @PostMapping("/bookSeries")
    public ResponseEntity<String> addBookSeries(@RequestBody Category Series){
        return feignCategoryService.addBookSeries(Series);
    }
    @DeleteMapping("/bookSeries")
    public ResponseEntity<String> deleteBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId){
        return feignCategoryService.deleteBookSeries(seriesId);
    }
    @GetMapping("/bookSeriesBySeriesId")
    public Category alterBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId){
        return feignCategoryService.alterBookSeries(seriesId);
    }

    @PutMapping("/bookSeries")
    public ResponseEntity<String> updateBookSeries(@RequestBody Category category){
        return feignCategoryService.updateBookSeries(category);
    }
}
