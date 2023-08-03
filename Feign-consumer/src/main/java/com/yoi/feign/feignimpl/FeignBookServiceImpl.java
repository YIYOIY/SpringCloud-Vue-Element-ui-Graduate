package com.yoi.feign.feignimpl;

import com.yoi.entity.Book;
import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.FeignBookService;
import org.springframework.stereotype.Component;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignBookServiceImpl implements FeignBookService {
    @Override
    public ReturnInfo index(String searchName,Integer BookpageNo) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo lookUp(Integer bookId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo selectBySeries(String seriesName) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo addBook(Book book) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo deleteBook(Integer bookId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo updateBook(Book book) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }
}
