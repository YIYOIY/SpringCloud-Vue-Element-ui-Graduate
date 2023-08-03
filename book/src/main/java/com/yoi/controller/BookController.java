package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoi.entity.Book;
import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.exception.SelfExcept;
import com.yoi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final static ObjectMapper om = new ObjectMapper();


    /**
     * 从redis中读取mysqlbook表的缓存，后续判断更新及删除操作时redis库的数据操作
     *
     * @param searchName
     * @param BookpageNo
     * @return
     */
    @CrossOrigin
    @GetMapping("/book")
    public ReturnInfo index(@RequestParam(value = "searchName",required = false)String searchName, @RequestParam(value = "BookpageNo",required = false) Integer BookpageNo) {
        ListOperations<String, String> redisList = stringRedisTemplate.opsForList();
        List<Book> all = null;
        if (BookpageNo == null) {
            BookpageNo = 1;
        }
        try {
            if (StringUtils.isEmpty(searchName)) {
                searchName = "";
            } else {
                List<String> list = redisList.range("books", 0, -1);
                ArrayList<Book> books = new ArrayList<>();
                if (list.size()!=0 && list != null) {
                    String finalSearchName = searchName;
                    list.stream().forEach(li -> {
                        Book book = null;
                        try {
                            book = om.readValue(li, Book.class);
                            if (book.getBookName().equals(finalSearchName)){
                                books.add(book);
                            }
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    if (books.size()>=1){
                        return new ReturnInfo(200,"书籍获取成功！",books);
                    }else {
                        return new ReturnInfo(404, "书籍查询失败，未收录此书籍！");
                    }
                }
            }
//                session.setAttribute("BookpageNo", BookpageNo);
//            页数所计算的页面显示条数，limit bookNumber，5
            int bookNumber = (BookpageNo - 1) * 5;

            List<String> list = redisList.range("books", 0, -1);
            ArrayList<Book> books = new ArrayList<>();
            if (list.stream().count() != 0) {
                list.stream().forEach(li -> {
                    Book book = null;
                    try {
                        book = om.readValue(li, Book.class);
                        books.add(book);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                all = books;
            } else {
                all = bookService.getAll(searchName, bookNumber);
                ArrayList<String> redisBooks = new ArrayList<>();
                all.stream().forEach(a -> {
                    try {
                        String s = om.writeValueAsString(a);
                        redisBooks.add(s);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                redisList.leftPushAll("books", redisBooks);
            }
//            数据库数据总数量
            long count = bookService.getCount(searchName);
            long pagecount = (count + 4) / 5;
//            session.setAttribute("Bookpagecount", pagecount);
            return new ReturnInfo(200,"书籍获取成功",all);
        } catch (Exception e) {
            throw new SelfExcept("bookController的index出现的问题" + e);
        }
    }


    @GetMapping("/lookup")
    public ReturnInfo lookUp(Integer bookId) {
        Book book = bookService.getById(bookId);
        if(book!=null) {
            return new ReturnInfo(200, "书籍查询成功！", book);
        }else {
            return new ReturnInfo(404, "书籍查询失败，未收录此书籍！");
        }
    }


    @GetMapping("/selectBySeries")
    public ReturnInfo selectBySeries(@RequestParam("seriesName") String seriesName) {
        System.out.println(seriesName);
        List<Book> bookList = bookService.selectBySeries(seriesName);
        return new ReturnInfo(200,"书籍类型获取成功！",bookList);
    }

//    @CrossOrigin
//    @ResponseBody
//    @GetMapping("/lookup/byType")
//    private List<Book> typeLookUp(String bookType) {
//        try {
////            List<Book> byType = bookService.getByType(bookType);
////            return byType;
//        } catch (Exception e) {
//            throw new SelfExcept("bookController的typelookup出现的问题");
//        }
//    }


    @PostMapping("/book")
    public ReturnInfo addBook(@RequestBody Book book) {
        try {
            stringRedisTemplate.delete("books");
            boolean addBook = bookService.addBook(book);
            if (addBook) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ADD_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ADD_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept("bookController的add出现的问题" + e);
        }
    }

    @DeleteMapping("/book")
    public ReturnInfo deleteBook(Integer bookId) {
        try {
            stringRedisTemplate.delete("books");
            boolean deleteBook = bookService.deleteBook(bookId);
            if (deleteBook) {
                return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.DELETE_FAILED);
            }
        } catch (Exception e) {
            throw new SelfExcept("bookController的delete出现的问题"+e);
        }
    }


    @PutMapping("/book")
    public ReturnInfo updateBook(@RequestBody Book book) {
        try {
            stringRedisTemplate.delete("books");
            boolean alterBook = bookService.updateBook(book);
            if (alterBook) {
//                添加书籍封面时是否可以返回一个url通过路由进行编程式跳转或者刷新
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_SUCCESS);
            } else {
                return new ReturnInfo().withEnumNoData(ReturnEnum.ALTER_FAILED);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new SelfExcept("bookController的update出现的问题");
        }
    }

}
