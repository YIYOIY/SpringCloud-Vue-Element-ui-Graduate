package com.yoi.feigncontroller;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Shopkeeper;
import com.yoi.feign.service.FeignShopkeeperService;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 20:03
 */
@Validated
@RestController
@RefreshScope
public class ShopkeeperFeignController {
    @Resource
    private FeignShopkeeperService feignShopkeeperService;

    @CrossOrigin
    @GetMapping("/shopkeeper/{adminId}/{pageNo}/{pageSize}/{searchName}")
    public ReturnInfo<PagePackage<Shopkeeper>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                                     @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                                     @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                                     @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName) {
        return feignShopkeeperService.index(adminId, pageNo, pageSize, searchName);
    }

    @GetMapping("/shopkeeper/{shopkeeperId}")
    public ReturnInfo<Shopkeeper> shopkeeperSelf(@NotNull @PathVariable("shopkeeperId") Long shopkeeperId) {
        return feignShopkeeperService.shopkeeperSelf(shopkeeperId);
    }

    @PostMapping("/add_shopkeeper")
    public ReturnInfo<String> addShopkeeper(@RequestBody Shopkeeper shopkeeper) {
        return feignShopkeeperService.addShopkeeper(shopkeeper);
    }

    @DeleteMapping("/shopkeeper")
    public ReturnInfo<Shopkeeper> deleteShopkeeper(@Valid @RequestBody Shopkeeper shopkeeper) {
        return feignShopkeeperService.deleteShopkeeper(shopkeeper);
    }

    @PutMapping("/shopkeeper")
    public ReturnInfo<Shopkeeper> updateShopkeeper(@Valid @RequestBody Shopkeeper shopkeeper) {
        return feignShopkeeperService.updateShopkeeper(shopkeeper);
    }
}
