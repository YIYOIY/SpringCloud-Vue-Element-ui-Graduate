package com.yoi.feign.feignimpl;

import com.yoi.entity.Category;
import com.yoi.feign.FeignCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignCategoryServiceImpl implements FeignCategoryService {
    @Override
    public List<Category> index(HttpServletRequest req,@RequestParam(value = "searchName",required = false) String searchName) {
        return null;
    }

    @Override
    public List<Category> getSeries() {
        return null;
    }

    @Override
    public ResponseEntity<String> addBookSeries(Category Series) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> deleteBookSeries(Integer seriesId) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public Category alterBookSeries(Integer seriesId) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateBookSeries(Category category) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }
}
