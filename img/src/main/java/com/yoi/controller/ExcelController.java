package com.yoi.controller;

import com.yoi.config.ExcelUtil;
import com.yoi.entity.Book;
import com.yoi.mapper.BookMapper;
import com.yoi.service.BookService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 游弋
 * @create 2023-06-08 1:33 PM
 */
@RestController
public class ExcelController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookService bookService;

    @RequestMapping("exportExcel")
    public void export(HttpServletResponse response) {

        List<Book> all = bookMapper.getAllForExcel();
        String[] titles = {"书籍ID", "书名", "作者","书籍简介","书籍详情", "系列名", "价格", "上架数量","出版社","上架日期","封面地址",};
        String[][] value = new String[all.size()][titles.length];
        for (int i = 0; i < all.size(); i++) {
            value[i][0] = String.valueOf(all.get(i).getBookId());
            value[i][1] = all.get(i).getBookName();
            value[i][2] = all.get(i).getBookAuthor();
            value[i][3] = all.get(i).getBookInfo();
            value[i][4] = all.get(i).getBookDetail();
            value[i][5] = all.get(i).getSeriesName();
            value[i][6] = String.valueOf(all.get(i).getBookPrice());
            value[i][7] = String.valueOf(all.get(i).getBookNum());
            value[i][8] = all.get(i).getBookFactory();
            value[i][9] = String.valueOf(all.get(i).getBookAddDate());
            value[i][10] = all.get(i).getBookPicture();
        }

        String name = "书籍信息";
        XSSFWorkbook xssfWorkBook = ExcelUtil.getXSSFWorkBook(name, titles, value);
        String excelName = name + UUID.randomUUID().toString() + ".xlsx";

        OutputStream outputStream = null;
        try {
            // 设置响应格式，让浏览器知道是下载操作
            response.setContentType("applicaton/x-mdownload");
            // 设置下载后的文件名
            response.setHeader("Content-Disposition", "atachment;filename=" + new String(excelName.getBytes("utf-8"), "ISO8859-1"));
            // 设置响应编码
            response.setContentType("text/html;charcet=UTF-8");
            // 建立输出流的连接
            outputStream = response.getOutputStream();
            // 将数据导出到Excel表格
            xssfWorkBook.write(outputStream);
            // 关闭输出流
            outputStream.close();
            xssfWorkBook.close();
        } catch (IOException e) {
            System.out.println("输出流错误");
            throw new RuntimeException(e + "输出流错误");
        }
    }


    @RequestMapping("importExcel")
    public void excelInput(MultipartFile photo) {
        Boolean books = stringRedisTemplate.delete("books");
        if (books){
            System.out.println("redis中的books缓存已清空");
        }
//        注意这个photo要和前端上传的upload的name一致即name=photo
        String excelFileName = photo.getOriginalFilename();     // 获取excel文件名
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(photo.getInputStream());
            List<Book> bookList = readExcelValue(workbook);
//            System.out.println(bookList.toString());
            bookList.forEach(e->{
//                此处可以修改便利导入数据库，注意sql命令并没有补全，目前仅仅是作为一个测试用的sql，有很大的问题
                System.out.println(e+"\n\n\n\n\n正在进行插入操作\n\n\n\n\n\n\n\n\n\n\n");
                bookService.addBook(e);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取Excel内容，将其放入对应的实体对象中
    private List<Book> readExcelValue(Workbook workbook) {
        int totalRows = 0;
        int totalCells = 0;
        // 获取第一张表
        Sheet sheet = workbook.getSheetAt(0);
        // 得到表的总行数
        totalRows = sheet.getPhysicalNumberOfRows();
        // 根据第一行（标题行）获得总列数，获得总列数得根据某行确定
        if (totalRows >= 1 && sheet.getRow(1) != null) {
            totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }

        List<Book> bookList = new ArrayList<>();

        // 逐行取数据，每行对应一条实体对象信息
        for (int rowNum = 2; rowNum < totalRows; rowNum++) {
            // 跳过标题行
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            String[] titles = {"bookId", "bookName", "bookAuthor",  "bookInfo", "bookDetail","seriesName","bookPrice","bookNum", "bookFactory",  "bookAddDate","bookPicture" };
            Book book = new Book();
            // 逐列取数据，每列对应一个实体对象属性
            for (int colNum = 0; colNum < totalCells; colNum++) {
                System.out.println("第" + (rowNum + 1) + "行，第" + (colNum + 1) + "列");
                Cell cell = row.getCell(colNum);
                Field declaredField = null;
                try {
                    declaredField = Book.class.getDeclaredField(titles[colNum]);
                    declaredField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (colNum == 0 ) {
                        // 如果是纯数字,将单元格类型转为String
                        if (cell.getCellTypeEnum() != CellType.STRING) {
                            cell.setCellType(CellType.STRING);
                        }
                        declaredField.set(book, Integer.valueOf(cell.getStringCellValue()));
                    }else if (colNum == 6||colNum==7){
                        // 如果是double,将单元格类型转为String
                        if (cell.getCellTypeEnum() != CellType.STRING) {
                            cell.setCellType(CellType.STRING);
                        }
                        declaredField.set(book, Double.valueOf(cell.getStringCellValue()));
                    }else if (colNum == 9){
                        // 如果是纯数字,将单元格类型转为String
                        if (cell.getCellTypeEnum() != CellType.STRING) {
                            cell.setCellType(CellType.STRING);
                        }
                        declaredField.set(book, Date.valueOf(cell.getStringCellValue()));
                    }
                    else {
                        if (cell.getCellTypeEnum() != CellType.STRING) {
                            cell.setCellType(CellType.STRING);
                        }
                        declaredField.set(book, cell.getStringCellValue());
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("反射错误！");
                    throw new RuntimeException(e);
                }
            }
            // 添加到booklist
            bookList.add(book);
        }
        return bookList;
    }
}
