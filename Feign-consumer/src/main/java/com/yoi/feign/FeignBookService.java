package com.yoi.feign;

import com.yoi.entity.Book;
import com.yoi.feign.feignimpl.FeignBookServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Component
@FeignClient(value = "book",fallback = FeignBookServiceImpl.class)
public interface FeignBookService {
    @GetMapping("/book")
    public List<Book> index(@RequestParam(value = "searchName",required = false)String searchName,@RequestParam(value = "BookpageNo",required = false) Integer BookpageNo);
    @GetMapping("/lookup")
    public Book lookUp(@RequestParam(value = "bookId",required = false)Integer bookId);
    @GetMapping("/selectBySeries")
    public List<Book> selectBySeries(@RequestParam(value = "seriesName") String seriesName);
    @PostMapping("/book")
    public ResponseEntity<String> addBook(@RequestBody Book book);
    @DeleteMapping("/book")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "bookId",required = false)Integer bookId);
    @PutMapping("/book")
    public ResponseEntity<String> updateBook(@RequestBody Book book);
}
