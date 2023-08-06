package com.yoi.feign.service;

import com.yoi.entity.Book;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.service.feignimpl.FeignBookServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Validated
@Component
@FeignClient(value = "book", fallback = FeignBookServiceImpl.class)
public interface FeignBookService {
    @CrossOrigin
    @GetMapping("/book/{searchName}/{pageNo}/{pageSize}")
    ReturnInfo<PagePackage<Book>> index(@PathVariable("searchName") String searchName,
                                        @Min(1) @PathVariable("pageNo") Integer pageNo,
                                        @Min(1) @PathVariable("pageSize") Integer pageSize);

    @GetMapping("/select_by_series/{seriesName}/{pageNo}/{pageSize}")
    ReturnInfo<PagePackage<Book>> selectBySeries(@NotNull @PathVariable("seriesName") String seriesName,
                                                 @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                 @Min(1) @PathVariable("pageSize") Integer pageSize);

    @GetMapping("/lookup/{bookId}")
    ReturnInfo<Book> lookUp(@NotNull @PathVariable("bookId") Long bookId);

    @PostMapping("/book")
    ReturnInfo<String> addBook(@RequestBody Book book);

    @DeleteMapping("/book")
    ReturnInfo<String> deleteBook(@Valid @RequestBody Book book);

    @PutMapping("/book")
    ReturnInfo<String> updateBook(@Valid @RequestBody Book book);
}
