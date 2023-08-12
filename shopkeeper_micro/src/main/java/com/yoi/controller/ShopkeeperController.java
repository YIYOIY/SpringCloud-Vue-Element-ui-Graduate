package com.yoi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Shopkeeper;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.ShopkeeperService;
import io.seata.spring.annotation.GlobalTransactional;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author 游弋
 * @create 2023-08-05 18:16
 */
@Validated
@RestController
@RefreshScope
public class ShopkeeperController {
    @Resource
    private ShopkeeperService shopkeeperService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    private final static ObjectMapper object = new ObjectMapper();

    /**
     * 为管理员提供的方法，用来查看以及管理商户列表
     *
     * @param adminId    操作的管理员的Id
     * @param pageNo     页面
     * @param pageSize   页面显示数量
     * @param searchName 搜索名称
     * @return 返回用户列表
     */
    @CrossOrigin
    @GetMapping("/shopkeeper/{adminId}/{pageNo}/{pageSize}/{searchName}")
    public ReturnInfo<PagePackage<Shopkeeper>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                                     @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                                     @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                                     @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName) {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

        if (searchName.equals("null")) {
            /*管理员未进行查询，或者已经查询过，点击翻页后，进入此操作， 如果管理员查询过数据将把查询的关键字继续使用查询下一页*/
            searchName = "";
        }

//          返回分页结果
        Page<Shopkeeper> all = shopkeeperService.getAll(searchName, pageNo, pageSize);
        if (!all.getRecords().isEmpty()) {
            op.set(adminId.toString(), searchName + LocalDateTime.now());
//          包装分页数据
            PagePackage<Shopkeeper> ShopkeeperPagePackage = new PagePackage<>(all.getRecords(), all.getCurrent(),
                    all.getPages(), all.getSize(), all.getTotal(), all.hasNext(), all.hasPrevious());
//          返回前端统一返回格式
            return new ReturnInfo<>(200, "商户信息获取成功", ShopkeeperPagePackage);
        }
        return new ReturnInfo<>(404, "商户信息为空");
    }

    /**
     * 用户个人信息页面的api，同时是管理员修改用户和用户自我修改信息的api
     *
     * @param shopkeeperId 商户id
     */
    @GetMapping("/shopkeeper/{shopkeeperId}")
    public ReturnInfo<Shopkeeper> shopkeeperSelf(@NotNull @PathVariable("shopkeeperId") Long shopkeeperId) {
        Shopkeeper byId = shopkeeperService.getById(shopkeeperId);
        if (byId != null) {
            return ReturnInfo.withEnumData(ReturnEnum.GET_SUCCESS, byId);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.GET_FAILED);
        }
    }

    /**
     * 添加用户
     *
     * @param shopkeeper 将要增加的用户信息
     * @return 返回增加操作结果
     */
    @PostMapping("/add_shopkeeper")
    public ReturnInfo<String> addShopkeeper(@RequestBody Shopkeeper shopkeeper) {
        if (shopkeeperService.addShopkeeper(shopkeeper)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.NAME_FAILED);
        }
    }

    /**
     * 删除商户
     *
     * @param shopkeeper 获取商户Id
     * @return 返回删除操作结果
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @DeleteMapping("/shopkeeper")
    public ReturnInfo<Shopkeeper> deleteShopkeeper(@Valid @RequestBody Shopkeeper shopkeeper) {
        if (shopkeeperService.deleteShopkeeper(shopkeeper)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }

    }

    /**
     * 修改商户
     *
     * @param shopkeeper 商户信息
     * @return 返回更新操作结果
     */
    @PutMapping("/shopkeeper")
    public ReturnInfo<Shopkeeper> updateShopkeeper(@Valid @RequestBody Shopkeeper shopkeeper) {
        if (shopkeeperService.updateShopkeeper(shopkeeper)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }
    }
}
