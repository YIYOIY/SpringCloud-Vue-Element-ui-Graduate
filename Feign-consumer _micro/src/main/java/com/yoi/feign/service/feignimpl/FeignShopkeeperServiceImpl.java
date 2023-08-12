package com.yoi.feign.service.feignimpl;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Shopkeeper;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.feign.service.FeignShopkeeperService;
import org.springframework.stereotype.Component;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignShopkeeperServiceImpl implements FeignShopkeeperService {

    @Override
    public ReturnInfo<PagePackage<Shopkeeper>> index(Long adminId, Integer pageNo, Integer pageSize, String searchName) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<Shopkeeper> shopkeeperSelf(Long shopkeeperId) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> addShopkeeper(Shopkeeper shopkeeper) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<Shopkeeper> deleteShopkeeper(Shopkeeper shopkeeper) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<Shopkeeper> updateShopkeeper(Shopkeeper shopkeeper) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }
}
