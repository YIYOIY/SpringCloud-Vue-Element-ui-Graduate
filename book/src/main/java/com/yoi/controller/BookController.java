package com.yoi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.Book;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.BookService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RefreshScope
public class BookController {
    @Resource
    private BookService bookService;

    /**
     * 商城首页，判断用户是否搜索书籍
     *
     * @param searchName 搜索名称
     * @param pageNo     页面数
     * @param pageSize   页面显示数量
     * @return 查询结果
     */
    @CrossOrigin
    @GetMapping("/book/{searchName}/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Book>> index(@PathVariable("searchName") String searchName,
                                               @Min(1) @PathVariable("pageNo") Integer pageNo,
                                               @Min(1) @PathVariable("pageSize") Integer pageSize) {
        /*用户是否搜索书籍*/
        if (searchName.equals("null")) {
            searchName = "";
        }
            /*
            用户查询了书籍，从MySQL读取
            */
        Page<Book> mysqlBooks = bookService.getAll(searchName, pageNo, pageSize);
        PagePackage<Book> bookPagePackage = new PagePackage<>(mysqlBooks.getRecords(), mysqlBooks.getCurrent(),
                mysqlBooks.getPages(), mysqlBooks.getSize(), mysqlBooks.getTotal(), mysqlBooks.hasNext(), mysqlBooks.hasPrevious());
        if (mysqlBooks.getRecords() != null && !mysqlBooks.getRecords().isEmpty()) {
            return new ReturnInfo<>(200, "书籍获取成功", bookPagePackage);
        } else {
            return new ReturnInfo<>(404, "未收录书籍");
        }
    }

    //    企业的书籍首页，根据企业id显示企业所拥有的书籍
    @CrossOrigin
    @GetMapping("/book/{searchName}/{pageNo}/{pageSize}/{shopkeeperId}")
    public ReturnInfo<PagePackage<Book>> shopkeeperIndex(@PathVariable("searchName") String searchName,
                                                         @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                         @Min(1) @PathVariable("pageSize") Integer pageSize,
                                                         @Min(1) @PathVariable("shopkeeperId") Long shopkeeperId) {
        /*企业是否搜索书籍*/
        if (searchName.equals("null")) {
            searchName = "";
        }
            /*
            企业查询了书籍，从MySQL读取
            */
        Page<Book> mysqlBooks = bookService.getShopkeeperAll(searchName, pageNo, pageSize, shopkeeperId);
        PagePackage<Book> bookPagePackage = new PagePackage<>(mysqlBooks.getRecords(), mysqlBooks.getCurrent(),
                mysqlBooks.getPages(), mysqlBooks.getSize(), mysqlBooks.getTotal(), mysqlBooks.hasNext(), mysqlBooks.hasPrevious());
        if (mysqlBooks.getRecords() != null && !mysqlBooks.getRecords().isEmpty()) {
            return new ReturnInfo<>(200, "书籍查询成功", bookPagePackage);
        } else {
            return new ReturnInfo<>(404, "未收录书籍");
        }
    }


    /**
     * 按照书籍系列名查询，书籍系列名不允许为空
     * 先从redis中查询与搜索书籍系列匹配的书籍，redis如果为空未命中再从mysql中查找
     *
     * @param seriesName 书记系列名
     * @param pageNo     查看的页面数
     * @param pageSize   页面显示条数
     * @return 返回查询结果
     */

    @GetMapping("/select_by_series/{seriesName}/{pageNo}/{pageSize}")
    public ReturnInfo<PagePackage<Book>> selectBySeries(@NotNull @PathVariable("seriesName") String seriesName,
                                                        @Min(1) @PathVariable("pageNo") Integer pageNo,
                                                        @Min(1) @PathVariable("pageSize") Integer pageSize) {
        Page<Book> mysqlBooks = bookService.selectBySeries(seriesName, pageNo, pageSize);
        if (!mysqlBooks.getRecords().isEmpty()) {
            PagePackage<Book> bookPagePackage = new PagePackage<>(mysqlBooks.getRecords(), mysqlBooks.getCurrent(),
                    mysqlBooks.getPages(), mysqlBooks.getSize(), mysqlBooks.getTotal(), mysqlBooks.hasNext(), mysqlBooks.hasPrevious());
            return new ReturnInfo<>(200, "书籍按照系列获取成功", bookPagePackage);
        } else {
            return new ReturnInfo<>(404, "系列书籍未收录");
        }

    }

    /**
     * 单个书籍的查询，书籍详情
     *
     * @param bookId 书籍Id
     * @return 返回单个书籍
     */
    @GetMapping("/lookup/{bookId}")
    public ReturnInfo<Book> lookUp(@NotNull @PathVariable("bookId") Long bookId) {
        Book book = bookService.getById(bookId);
        if (book != null) {
            return new ReturnInfo<>(200, "书籍查询成功！", book);
        } else {
            return new ReturnInfo<>(404, "书籍查询失败，未收录此书籍！");
        }
    }

    @PostMapping("/book")
    public ReturnInfo<String> addBook(@RequestBody Book book) {
        if (bookService.addBook(book)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_FAILED);
        }
    }

    @DeleteMapping("/book")
    public ReturnInfo<String> deleteBook(@Valid @RequestBody Book book) {
        if (bookService.deleteBook(book)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }
    }

    @PutMapping("/book")
    public ReturnInfo<String> updateBook(@Valid @RequestBody Book book) {
        if (bookService.updateBook(book)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }

    }

}
