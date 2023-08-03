package com.yoi.feign.feignimpl;

import com.yoi.entity.Order;
import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.feign.FeignOrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignOrderServiceImpl implements FeignOrderService {
    @Override
    public ReturnInfo index(@RequestParam(value = "pageNo",required = false) Integer pageNo, @RequestParam(value = "userId",required = false)  Integer userId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo userPageCount(Integer userId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo adminPageCount() {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo indexAdmin(Integer pageNo) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo getOrderById(Integer orderId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo addOrder(Order order) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo deleteOrder(Integer orderId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo buybag(Integer orderId, Integer num, Integer bookId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }
}
