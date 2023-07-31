package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yoi.entity.Category;
import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.exception.SelfExcept;
import com.yoi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@RestController
public class CategoryController {
    @Autowired
    private CategoryService seriesService;
    @CrossOrigin
    @GetMapping("/bookSeries")
    public ReturnInfo index(HttpServletRequest req, String searchName) {
        HttpSession session = req.getSession();
        try {
            if (StringUtils.isEmpty(searchName)) {
            String keyword = (String) session.getAttribute("BookSerieskeyword");
            if (StringUtils.isNotEmpty(keyword)){
                    searchName=keyword;
                }else {
                    searchName="";
                }
            }else {
                session.setAttribute("BookSerieskeyword", searchName);
            }
            return new ReturnInfo(200,"查询成功！",seriesService.getAll(searchName));
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的index出现的问题");
        }
    }

    @GetMapping("/series")
    public ReturnInfo getSeries(){
        List<Category> seriesName = seriesService.getSeriesName();
        return new ReturnInfo(200,"查询成功！",seriesName);
    }

    @PostMapping("/bookSeries")
    public ReturnInfo addBookSeries(@RequestBody Category Series) {
        try {
            boolean addBookSeries = seriesService.addBookSeries(Series);
            if (addBookSeries) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ADD_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_FAILED);
            }
        }catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的add出现的问题");
        }
    }

    @DeleteMapping("/bookSeries")
    public ReturnInfo deleteBookSeries(Integer seriesId) {
        try {
                boolean deleteBookSeries = seriesService.deleteBookSeries(seriesId);
                if (deleteBookSeries) {
                    return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_SUCCESS);
                } else {
                    return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_FAILED);
                }
//            }
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的delete出现的问题");
        }
    }


    @GetMapping("/bookSeriesBySeriesId")
    public ReturnInfo alterBookSeries(Integer seriesId) {
        try {
            return new ReturnInfo(200,"查询成功！",seriesService.getBySeriesId(seriesId));
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的alter出现的问题");
        }
    }

    @PutMapping("/bookSeries")
    public ReturnInfo updateBookSeries(@RequestBody Category category) {
        try {
            boolean alterBookSeries = seriesService.updateBookSeries(category);
            if (alterBookSeries) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的update出现的问题");
        }
    }
}
