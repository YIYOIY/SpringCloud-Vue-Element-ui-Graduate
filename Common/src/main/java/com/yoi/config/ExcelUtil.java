package com.yoi.config;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
/**
 * @author 游弋
 * @create 2023-06-08 1:28 PM
 */
public class ExcelUtil {
    public static XSSFWorkbook getXSSFWorkBook(String name,String[] titles,String[][] data){
//        创建一个excel文件对应的对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
//        添加一个sheet，对应excel的一个sheet
        XSSFSheet sheet = xssfWorkbook.createSheet(name);
//        创建单元格样式，并设置值表头 设置表头居中,创建一个居中格式
        XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

// 设置默认列宽，width为字符个数
        sheet.setDefaultColumnWidth(20);
// 设置第columnIndex+1列的列宽，单位为字符宽度的1/256
        sheet.setColumnWidth(3,50*256);
        sheet.setColumnWidth(4,50*256);
        sheet.setColumnWidth(10,40*256);
// 设置第5列的列宽为20个字符宽度
// sheet.setColumnWidth(4, 20*256)

//        对此sheet产生标题行
        XSSFRow row = sheet.createRow(0);
        XSSFCell sheetHeader = row.createCell(0);
        sheetHeader.setCellValue(name);
        sheetHeader.setCellStyle(cellStyle);
//        设置第一行表头合并列
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, titles.length-1);
        sheet.addMergedRegion(region);


        row = sheet.createRow(1);
        XSSFCell sheetTitle=null;
//            产生表头
        for (int i = 0; i < titles.length; i++) {
//            创建标题行的列
            sheetTitle = row.createCell(i);
//            为列赋值标题名
            sheetTitle.setCellValue(titles[i]);
//             设置列的样式
            sheetTitle.setCellStyle(cellStyle);
        }

//          设置内容
        for (int i = 0; i <data.length; i++) {
//            因为第0行作为标题,第一行作为表头了，所以从第二行开始
            row = sheet.createRow(i + 2);
            for (int j = 0; j <data[i].length ; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(data[i][j]);
                cell.setCellStyle(cellStyle);
            }
        }
        return xssfWorkbook;
    }

}