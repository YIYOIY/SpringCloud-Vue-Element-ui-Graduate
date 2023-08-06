package com.yoi.feign.service.feignimpl;

import com.yoi.entity.Book;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.feign.service.FeignBookService;
import org.springframework.stereotype.Component;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignBookServiceImpl implements FeignBookService {
    @Override
    public ReturnInfo<PagePackage<Book>> index(String searchName, Integer pageNo, Integer pageSize) {
        return  ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<PagePackage<Book>> selectBySeries(String seriesName, Integer pageNo, Integer pageSize) {
        return  ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<Book> lookUp(Long bookId) {
        return  ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> addBook(Book book) {
        return  ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> deleteBook(Book book) {
        return  ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> updateBook(Book book) {
        return  ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }
}
