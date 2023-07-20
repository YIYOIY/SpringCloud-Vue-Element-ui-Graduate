package com.yoi.feigncontroller;

import com.yoi.entity.Book;
import com.yoi.feign.FeignBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@RestController
public class BookFeignController {
    @Autowired
    private FeignBookService feignBookService;
    @GetMapping("/book")
    public List<Book> index(@RequestParam(value = "searchName",required = false)String searchName,@RequestParam(value = "bookId",required = false) Integer BookpageNo){
        return feignBookService.index(searchName, BookpageNo);
    }
    @GetMapping("/lookup")
    public Book lookUp(@RequestParam(value = "bookId",required = false)Integer bookId){
        return feignBookService.lookUp(bookId);
    }
    @GetMapping("/selectBySeries")
    public List<Book> selectBySeries(@RequestParam("seriesName") String seriesName){
        System.out.println(seriesName);
        return feignBookService.selectBySeries(seriesName);
    }
    @PostMapping("/book")
    public ResponseEntity<String> addBook(@RequestBody Book book){
        return feignBookService.addBook(book);
    }
    @DeleteMapping("/book")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "bookId",required = false)Integer bookId){
        return feignBookService.deleteBook(bookId);
    }
    @PutMapping("/book")
    public ResponseEntity<String> updateBook(@RequestBody Book book){
        return feignBookService.updateBook(book);
    }
}
