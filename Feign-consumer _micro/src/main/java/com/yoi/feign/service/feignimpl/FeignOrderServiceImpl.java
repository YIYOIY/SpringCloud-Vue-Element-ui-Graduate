package com.yoi.feign.service.feignimpl;

import com.yoi.entity.Order;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.feign.service.FeignOrderService;
import org.springframework.stereotype.Component;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignOrderServiceImpl implements FeignOrderService {
    @Override
    public ReturnInfo<PagePackage<Order>> userListOrder(Integer pageNo, Integer pageSize, Long userId) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<PagePackage<Order>> shopkeeperListOrder(Integer pageNo, Integer pageSize, Long shopkeeperId) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<PagePackage<Order>> adminListOrder(Integer pageNo, Integer pageSize) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<Order> getOrderById(Long orderId) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> addOrder(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> deleteOrder(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> buy(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> confirmOrder(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> backOrder(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> commentOrder(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> alterOrder(Order order) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }
}
