package com.yoi.feign.feignimpl;

import com.yoi.entity.ReturnEnum;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.feign.FeignUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Component
public class FeignUserServiceImpl implements FeignUserService {
    @Override
    public ReturnInfo index(HttpServletRequest req, @RequestParam(value = "searchName",required = false) String searchName,@RequestParam(value = "oper",required = false) String oper) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo userSelf(Integer userId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo addUser(User user) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo deleteUser(Integer userId) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }

    @Override
    public ReturnInfo updateUser(User user) {
        return new ReturnInfo().withEnumNoData(ReturnEnum.WAIT);
    }
}
