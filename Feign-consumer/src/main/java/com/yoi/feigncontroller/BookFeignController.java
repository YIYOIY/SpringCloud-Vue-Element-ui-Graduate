package com.yoi.feigncontroller;

import com.yoi.entity.Book;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.service.FeignBookService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@Validated
@RestController
@RefreshScope
public class BookFeignController {
    @Resource
    private FeignBookService feignBookService;
    @CrossOrigin
    @GetMapping("/book/{searchName}/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Book>> index(@PathVariable("searchName") String searchName,
                                               @Min(1) @PathVariable("pageNo") Integer pageNo,
                                               @Min(1) @PathVariable("pageSize") Integer pageSize) {
        return feignBookService.index(searchName, pageNo,pageSize);
    }

    @CrossOrigin
    @GetMapping("/book/{searchName}/{pageNo}/{pageSize}/{shopkeeperId}")
    public ReturnInfo<PagePackage<Book>> shopkeeperIndex(@PathVariable("searchName") String searchName,
                                                         @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                         @Min(1) @PathVariable("pageSize") Integer pageSize,
                                                         @Min(1) @PathVariable("shopkeeperId") Long shopkeeperId) {
        return feignBookService.shopkeeperIndex(searchName,pageNo,pageSize,shopkeeperId);

    }
    @GetMapping("/select_by_series/{seriesName}/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Book>> selectBySeries(@NotNull @PathVariable("seriesName") String seriesName,
                                                        @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                        @Min(1) @PathVariable("pageSize") Integer pageSize) {
        System.out.println(seriesName);
        return feignBookService.selectBySeries(seriesName,pageNo,pageSize);
    }
    @GetMapping("/lookup/{bookId}")
    public ReturnInfo<Book> lookUp(@NotNull @PathVariable("bookId") Long bookId) {
        return feignBookService.lookUp(bookId);
    }
    @PostMapping("/book")
    public ReturnInfo<String> addBook(@RequestBody Book book) {
        return feignBookService.addBook(book);
    }
    @DeleteMapping("/book")
    public ReturnInfo<String> deleteBook(@Valid @RequestBody Book book) {
        return feignBookService.deleteBook(book);
    }
    @PutMapping("/book")
    public ReturnInfo<String> updateBook(@Valid @RequestBody Book book) {
        return feignBookService.updateBook(book);
    }
}
