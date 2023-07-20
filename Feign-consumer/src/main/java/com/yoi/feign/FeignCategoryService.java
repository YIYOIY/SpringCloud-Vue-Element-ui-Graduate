package com.yoi.feign;

import com.yoi.entity.Category;
import com.yoi.feign.feignimpl.FeignCategoryServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
@FeignClient(value = "category",fallback = FeignCategoryServiceImpl.class)
public interface FeignCategoryService {
    @GetMapping("/bookSeries")
    public List<Category> index(@RequestParam(value = "req",required = false) HttpServletRequest req, @RequestParam(value = "searchName",required = false)String searchName);
    @GetMapping("/series")
    public List<Category> getSeries();
    @PostMapping("/bookSeries")
    public ResponseEntity<String> addBookSeries(@RequestBody Category Series);
    @DeleteMapping("/bookSeries")
    public ResponseEntity<String> deleteBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId);
    @GetMapping("/bookSeriesBySeriesId")
    public Category alterBookSeries(@RequestParam(value = "seriesId",required = false)Integer seriesId);
    @PutMapping("/bookSeries")
    public ResponseEntity<String> updateBookSeries(@RequestBody Category category);
}
