package com.yoi.feign.feignimpl;

import com.yoi.entity.Category;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.FeignCategoryService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignCategoryServiceImpl implements FeignCategoryService {
    @Override
    public ReturnInfo index(HttpServletRequest req,String searchName) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo getSeries() {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo addBookSeries(Category Series) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo deleteBookSeries(Integer seriesId) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo alterBookSeries(Integer seriesId) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ReturnInfo updateBookSeries(Category category) {
        return new ReturnInfo(404,"后台服务超时或宕机，稍后再试！");
    }
}
