package com.yoi.feign.service.feignimpl;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.feign.service.FeignUserService;
import org.springframework.stereotype.Component;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignUserServiceImpl implements FeignUserService {
    @Override
    public ReturnInfo<PagePackage<User>> index(Long adminId, Integer pageNo, Integer pageSize,
                                               String searchName, String operate) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<User> userSelf(Long userId) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<String> addUser(User user) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<User> deleteUser(User user) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo<User> updateUser(User user) {
        return ReturnInfo.withEnumNoData(ReturnEnum.WAIT);
    }
}
