package com.yoi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Shopkeeper;

/**
 * @author 游弋
 * @create 2023-08-05 6:13
 */
public interface ShopkeeperService extends IService<Shopkeeper> {
    Shopkeeper Login(Shopkeeper shopkeeper);

    Shopkeeper getById(Long shopkeeperId);

    Page<Shopkeeper> getAll(String keyword, Integer pageNo, Integer pageSize);

    boolean addShopkeeper(Shopkeeper shopkeeper);

    boolean deleteShopkeeper(Long shopkeeperId);

    boolean updateShopkeeper(Shopkeeper shopkeeper);
}
