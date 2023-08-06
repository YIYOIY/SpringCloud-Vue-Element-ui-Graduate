package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoi.entity.Book;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.BookService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RefreshScope
public class BookController {
    @Resource
    private BookService bookService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static ObjectMapper om = new ObjectMapper();

    /**
     * 商城首页，判断用户是否搜索书籍
     * 用户搜索书籍将从redis中先查找，redis未命中再从mysql查找
     * 用户未查询书籍。一样先充redis中查找。redis未命中再从mysql查找
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
        boolean redisIsNull = false;
        ListOperations<String, String> redisList = stringRedisTemplate.opsForList();
        /*用户是否搜索书籍*/
        if (StringUtils.isEmpty(searchName)) {
            searchName = "";
        } else {
            /*用户搜索后从redis进行判断，查询出用户的书籍返回*/
            List<String> list = redisList.range("books", 0, -1);
            ArrayList<Book> books = new ArrayList<>();
            assert list != null;
//            如果redis中数据库有数据
            if (!list.isEmpty()) {
//            redis中的总页数
                long pageNoo = (list.size() + 4) / 5;
                String finalSearchName = searchName;
                list.forEach(li -> {
                    Book book;
                    try {
                        book = om.readValue(li, Book.class);
//                    页面展示数量
                        if (books.size() < pageSize && book.getBookName().equals(finalSearchName)) {
                            books.add(book);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                /*如果redis中有此书籍，返回匹配结果*/
                if (!books.isEmpty()) {
                    PagePackage<Book> redisSearchBooksPagePackage = new PagePackage<>(books, Integer.toUnsignedLong(pageNo),
                            pageNoo, Integer.toUnsignedLong(pageSize), Integer.toUnsignedLong(list.size()), pageNo < pageNoo, pageNo > 1);
                    return new ReturnInfo<>(200, "书籍获取成功", redisSearchBooksPagePackage);
                } else {
                    return new ReturnInfo<>(404, "未收录此书籍！");
                }
            } else {
//                redis数据库为空
                redisIsNull = true;
            }
        }

        /*
        1，用户没有搜索书籍，用以全部展示，先判断redis数据库有没有，有的话从数据库进行读取，减少mysql压力
        2，用户搜索书籍但是，redis数据库为空,用redisIsNull进行分流
        */
        if (!redisIsNull) {
//            用户没有查询书籍才能进入此流程
            List<String> list = redisList.range("books", 0, -1);
            List<Book> redisBooks = new ArrayList<>();
            assert list != null;
            if (!list.isEmpty()) {
//            redis中的总页数
                long pageNoo = (list.size() + 4) / 5;
                list.forEach(li -> {
                    Book book;
                    try {
                        book = om.readValue(li, Book.class);
//                    页面展示数量
                        if (redisBooks.size() < pageSize) {
                            redisBooks.add(book);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                if (!redisBooks.isEmpty()) {
                    PagePackage<Book> redisBookPagePackage = new PagePackage<>(redisBooks, Integer.toUnsignedLong(pageNo),
                            pageNoo, Integer.toUnsignedLong(pageSize), Integer.toUnsignedLong(list.size()), pageNo < pageNoo, pageNo > 1);
                    return new ReturnInfo<>(200, "书籍获取成功", redisBookPagePackage);
                } else {
                    return new ReturnInfo<>(404, "未收录书籍");
                }

            } else {
                /*
                用户没有查询书籍，且redis中没有数据，从MySQL读取,并存入redis数据库中
                */
                return getPagePackageReturnInfo(searchName, pageNo, pageSize, redisList);
            }
        } else {
            /*
            用户查询了书籍，且redis中没有数据，从MySQL读取,并存入redis数据库中
            */
            return getPagePackageReturnInfo(searchName, pageNo, pageSize, redisList);
        }
    }


    /**
     * 把首获取首页的书籍方法抽取出来，这里是redis中没有数据的情况下会走到这个方法
     * 这个方法会重新给redis存储数据，并且封装mysql获取的数据返回给前端
     * @param searchName 搜索名
     * @param pageNo 页面数
     * @param pageSize 页面显示数量
     * @param redisList redis操作对象
     * @return 返回封装好的books
     */

    private ReturnInfo<PagePackage<Book>> getPagePackageReturnInfo(String searchName, Integer pageNo, Integer pageSize, ListOperations<String, String> redisList) {
        Page<Book> mysqlBooks = bookService.getAll(searchName, pageNo, pageSize);
        if (!mysqlBooks.getRecords().isEmpty()) {
//            给redis数据库重新赋予mysql数据库中books的信息
            renewRedisDataFromMysql(redisList, mysqlBooks);

            PagePackage<Book> bookPagePackage = new PagePackage<>(mysqlBooks.getRecords(), mysqlBooks.getCurrent(),
                    mysqlBooks.getPages(), mysqlBooks.getSize(), mysqlBooks.getTotal(), mysqlBooks.hasNext(), mysqlBooks.hasPrevious());
            return new ReturnInfo<>(200, "书籍获取成功", bookPagePackage);
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

        ListOperations<String, String> redisList = stringRedisTemplate.opsForList();

        /*用户使用有效的系列名来查询书籍,从redis进行判断，查询出用户的书籍系列返回*/
        List<String> list = redisList.range("books", 0, -1);
        ArrayList<Book> books = new ArrayList<>();
        assert list != null;
        if (!list.isEmpty()) {
//            redis中的总页数
            long pageNoo = (list.size() + 4) / 5;
            list.forEach(li -> {
                Book book;
                try {
                    book = om.readValue(li, Book.class);
//                    页面展示数量
                    if (books.size() < pageSize && book.getSeries().getSeriesName().equals(seriesName)) {
                        books.add(book);
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

            /*如果redis中有此书籍，返回匹配结果*/
            if (!books.isEmpty()) {
                PagePackage<Book> redisSearchBooksPagePackage = new PagePackage<>(books, Integer.toUnsignedLong(pageNo),
                        pageNoo, Integer.toUnsignedLong(pageSize), Integer.toUnsignedLong(list.size()), pageNo < pageNoo, pageNo > 1);
                return new ReturnInfo<>(200, "书籍按系列查询获取成功", redisSearchBooksPagePackage);
            } else {
                return new ReturnInfo<>(404, "书籍系列查询失败，未收录此书籍！");
            }
        } else {
            /*redis数据库为空，未命中，从数据库进行读取,并存入redis数据库中*/
            Page<Book> mysqlBooks = bookService.selectBySeries(seriesName, pageNo, pageSize);
            if (!mysqlBooks.getRecords().isEmpty()) {
                //            给redis数据库重新赋予mysql数据库中books的信息
                renewRedisDataFromMysql(redisList, mysqlBooks);

                PagePackage<Book> bookPagePackage = new PagePackage<>(mysqlBooks.getRecords(), mysqlBooks.getCurrent(),
                        mysqlBooks.getPages(), mysqlBooks.getSize(), mysqlBooks.getTotal(), mysqlBooks.hasNext(), mysqlBooks.hasPrevious());
                return new ReturnInfo<>(200, "书籍按照系列获取成功", bookPagePackage);
            } else {
                return new ReturnInfo<>(404, "系列书籍未收录");
            }
        }
    }

    /**
     * 给redis数据库重新赋予mysql数据库中books的信息
     * @param redisList redis操作对象
     * @param mysqlBooks mysql数据库中books
     */
    private void renewRedisDataFromMysql(ListOperations<String, String> redisList, Page<Book> mysqlBooks) {
        ArrayList<String> newBooksToRedis = new ArrayList<>();
        mysqlBooks.getRecords().forEach(a -> {
            try {
                String s = om.writeValueAsString(a);
                newBooksToRedis.add(s);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        redisList.leftPushAll("books", newBooksToRedis);
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
        stringRedisTemplate.delete("books");
        if (bookService.addBook(book)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_FAILED);
        }
    }

    @DeleteMapping("/book")
    public ReturnInfo<String> deleteBook(@Valid @RequestBody Book book) {
        stringRedisTemplate.delete("books");
        if (bookService.deleteBook(book.getId())) {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }
    }

    @PutMapping("/book")
    public ReturnInfo<String> updateBook(@Valid @RequestBody Book book) {
        stringRedisTemplate.delete("books");
        if (bookService.updateBook(book)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }

    }

}
