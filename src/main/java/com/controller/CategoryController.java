package com.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.entity.Category;
import com.exception.SelfExcept;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
public class CategoryController {
    @Autowired
    CategoryService seriesService;

    @ResponseBody
    @CrossOrigin
    @GetMapping("/bookSeries")
    public List<Category> index(HttpServletRequest req, String searchName) {
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
            return seriesService.getAll(searchName);
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的index出现的问题");
        }
    }


    @ResponseBody
    @GetMapping("/series")
    public List<Category> getSeries(){
        List<Category> seriesName = seriesService.getSeriesName();
        return seriesName;
    }

    @PostMapping("/bookSeries")
    public ResponseEntity<String> addBookSeries(@RequestBody Category Series) {
        try {
            boolean addBookSeries = seriesService.addBookSeries(Series);
            if (addBookSeries) {
                return ResponseEntity.ok("添加成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
            }
        }catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的add出现的问题");
        }
    }

    @DeleteMapping("/bookSeries")
    public ResponseEntity<String> deleteBookSeries(Integer seriesId) {
        try {
            //            删除前检查购物车
//            Long aLong = seriesService.checkBagData(seriesId);
//            if (aLong > 0) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败,用户购物车存在此类型书籍");
//            } else {
                boolean deleteBookSeries = seriesService.deleteBookSeries(seriesId);
                if (deleteBookSeries) {
                    return ResponseEntity.ok("删除成功");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
                }
//            }
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的delete出现的问题");
        }
    }

    @ResponseBody
    @GetMapping("/bookSeriesBySeriesId")
    public Category alterBookSeries(Integer seriesId) {
        try {
            return seriesService.getBySeriesId(seriesId);
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的alter出现的问题");
        }
    }

    @PutMapping("/bookSeries")
    public ResponseEntity<String> updateBookSeries(@RequestBody Category category) {
        try {
            boolean alterBookSeries = seriesService.updateBookSeries(category);
            if (alterBookSeries) {
                return ResponseEntity.ok("更新成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
            }
        } catch (Exception e) {
            throw new SelfExcept(e+"bookSeriesController的update出现的问题");
        }
    }
}
