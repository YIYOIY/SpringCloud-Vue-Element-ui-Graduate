package com.yoi.feign.service;

import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.Shopkeeper;
import com.yoi.feign.service.feignimpl.FeignShopkeeperServiceImpl;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author 游弋
 * @create 2023-07-19 12:04
 */
@Validated
@Component
@FeignClient(value = "shopkeeper",fallback = FeignShopkeeperServiceImpl.class)
public interface FeignShopkeeperService {
    @CrossOrigin
    @GetMapping("/Shopkeeper/{adminId}/{pageNo}/{pageSize}/{searchName}/{operate}")
     ReturnInfo<PagePackage<Shopkeeper>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                                     @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                                     @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                                     @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName,
                                                     @Length(max = 100) @PathVariable(value = "operate", required = false) String operate) ;
    @GetMapping("/shopkeeper/{shopkeeperId}")
     ReturnInfo<Shopkeeper> shopkeeperSelf(@NotNull @PathVariable("shopkeeperId") Long shopkeeperId) ;
    @PostMapping("/add_shopkeeper")
     ReturnInfo<String> addShopkeeper(@RequestBody Shopkeeper shopkeeper) ;
    @DeleteMapping("/shopkeeper")
     ReturnInfo<Shopkeeper> deleteShopkeeper(@Valid @RequestBody Shopkeeper shopkeeper) ;
    @PutMapping("/shopkeeper")
     ReturnInfo<Shopkeeper> updateShopkeeper(@Valid @RequestBody Shopkeeper shopkeeper) ;
}
