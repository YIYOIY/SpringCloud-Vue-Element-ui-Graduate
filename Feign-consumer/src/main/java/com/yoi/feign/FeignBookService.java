package com.yoi.feign;

import com.yoi.entity.Book;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.feignimpl.FeignBookServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
@Component
@FeignClient(value = "book",fallback = FeignBookServiceImpl.class)
public interface FeignBookService {
    @GetMapping("/book")
    public ReturnInfo index(@RequestParam(value = "searchName",required = false)String searchName, @RequestParam(value = "BookpageNo",required = false) Integer BookpageNo);
    @GetMapping("/lookup")
    public ReturnInfo lookUp(@RequestParam(value = "bookId",required = false)Integer bookId);
    @GetMapping("/selectBySeries")
    public ReturnInfo selectBySeries(@RequestParam(value = "seriesName") String seriesName);
    @PostMapping("/book")
    public ReturnInfo addBook(@RequestBody Book book);
    @DeleteMapping("/book")
    public ReturnInfo deleteBook(@RequestParam(value = "bookId",required = false)Integer bookId);
    @PutMapping("/book")
    public ReturnInfo updateBook(@RequestBody Book book);
}
