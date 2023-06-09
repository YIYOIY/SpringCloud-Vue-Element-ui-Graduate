package com.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.entity.Book;
import com.exception.SelfExcept;
import com.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

//    @ResponseBody
    @CrossOrigin
    @GetMapping("/book")
    private List<Book> index(HttpServletRequest req, String searchName, String oper, Integer BookpageNo) {
        HttpSession session = req.getSession();
        if (BookpageNo == null) {
            BookpageNo = 1;
        }
        try {
            if (StringUtils.isNotEmpty(oper) && oper.equals("getByName")) {
                if (StringUtils.isEmpty(searchName)) {
                    searchName = "";
                }
                session.setAttribute("Bookkeyword", searchName);
            } else {
                String keyword = (String) session.getAttribute("Bookkeyword");
                if (StringUtils.isNotEmpty(keyword)) {
                    searchName = keyword;
                } else {
                    searchName = "";
                }
                session.setAttribute("BookpageNo", BookpageNo);
            }
//            页数所计算的页面显示条数，limit bookNumber，5
            int bookNumber=(BookpageNo-1)*5;

            List<Book> all = bookService.getAll(searchName, bookNumber);
            session.setAttribute("list", all);

//            数据库数据总数量
            long count = bookService.getCount(searchName);
            long pagecount = (count + 4) / 5;
            System.out.println(pagecount);

//            session.setAttribute("Bookpagecount", pagecount);
            return all;

        } catch (Exception e) {
            throw new SelfExcept("bookController的index出现的问题"+e);
        }
    }


    @ResponseBody
    @GetMapping("/lookup")
    private Book lookUp(Integer bookId) {
            Book book = bookService.getById(bookId);
            return book;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/lookup/byType")
    private List<Book> typeLookUp(String bookType) {
        try {
            List<Book> byType = bookService.getByType(bookType);
            return byType;
        } catch (Exception e) {
            throw new SelfExcept("bookController的typelookup出现的问题");
        }
    }



    @PostMapping("/book")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            System.out.println(book);
            boolean addBook = bookService.addBook(book);
            if (addBook) {
                return ResponseEntity.ok("添加成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new SelfExcept("bookController的add出现的问题");
        } finally {
//            GetDriver.closeresource(getconnection, null);
        }
    }

    @DeleteMapping("/book")
    public ResponseEntity<String> deleteBook(Integer bookId) {
        try {
            //            删除前检查购物车
            Long aLong = bookService.checkBagData(bookId);
            if (aLong > 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败,改图书被某些用户加入购物车!");
            } else {
                boolean deleteBook = bookService.deleteBook(bookId);
                if (deleteBook) {
                    return ResponseEntity.ok("删除成功");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
                }
            }
        } catch (Exception e) {
            throw new SelfExcept("bookController的delete出现的问题");
        } finally {
//            GetDriver.closeresource(getconnection, null);
        }
    }


    @PutMapping("/book")
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        try {
            System.out.println(book.toString());
            boolean alterBook = bookService.updateBook(book);
            if (alterBook) {
//                添加书籍封面时是否可以返回一个url通过路由进行编程式跳转或者刷新
                return ResponseEntity.ok("修改成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new SelfExcept("bookController的update出现的问题");
        }
    }


//    @GetMapping("/book/update")
//    public String alterBook(HttpServletRequest req, Integer BookId) {
//        try {
//            Book byId = bookService.getById(BookId);
//            return "book/alterBook";
//        } catch (Exception e) {
//            throw new SelfExcept("bookController的alter出现的问题");
//        }
//    }

    //    bookNo, bookName, bookAuthor, bookPicture, bookFactory, bookPrice, bookSeriesName,bookAddDate)


//    使用vue进行管理用户登陆之后就不需要专门的退出方法了
//    public String loginOutPage() {
//        return "loginOut:book.do";
//    }


    //    使用了element导航后是否还需要单独设置一个首页
//    @GetMapping("/admin/index")
//    public String adminIndex(HttpServletRequest req, String searchName, String oper, Integer AdminBookpageNo) {
//        Connection getconnection = null;
//        HttpSession session = req.getSession();
//        if (AdminBookpageNo == null) {
//            AdminBookpageNo = 1;
//        }
//
//        try {
//            if (StringEmpty.isNotEmpty(oper) && oper.equals("getByName")) {
//                if (StringEmpty.isEmpty(searchName)) {
//                    searchName = "";
//                }
//                session.setAttribute("BookAdminkeyword", searchName);
//            } else {
//                String keyword = (String) session.getAttribute("BookAdminkeyword");
//                if (StringEmpty.isNotEmpty(keyword)) {
//                    searchName = keyword;
//                } else {
//                    searchName = "";
//                }
//                session.setAttribute("AdminBookpageNo", AdminBookpageNo);
//            }
//            getconnection = ConnectionUtil.getConn();
//            List<Book> all = book.getAll(getconnection, searchName, AdminBookpageNo);
//            session.setAttribute("list", all);
//
//            long count = book.getCount(getconnection, searchName);
//            long pagecount = (count + 4) / 5;
//            System.out.println(pagecount);
//            session.setAttribute("AdminBookpagecount", pagecount);
//
//            return "book/manageBook";
//
//        } catch (Exception e) {
//            throw new SelfExcept("bookController的adminIndex出现的问题");
//        } finally {
////            GetDriver.closeresource(getconnection, null);
//        }
//    }

}
