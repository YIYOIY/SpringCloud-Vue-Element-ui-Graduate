package com.yoi.feign.feignimpl;

import com.yoi.entity.Book;
import com.yoi.feign.FeignBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignBookServiceImpl implements FeignBookService {
    @Override
    public List<Book> index(@RequestParam(value = "searchName",required = false)String searchName,@RequestParam(value = "BookpageNo",required = false) Integer BookpageNo) {
        return null;
    }

    @Override
    public Book lookUp(Integer bookId) {
        return null;
    }

    @Override
    public List<Book> selectBySeries(String seriesName) {
        return null;
    }

    @Override
    public ResponseEntity<String> addBook(Book book) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> deleteBook(Integer bookId) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }

    @Override
    public ResponseEntity<String> updateBook(Book book) {
        return ResponseEntity.ok("后台服务超时或宕机，稍后再试！");
    }
}
