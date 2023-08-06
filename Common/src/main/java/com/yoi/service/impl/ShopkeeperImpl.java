package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Shopkeeper;
import com.yoi.mapper.ShopkeeperMapper;
import com.yoi.service.ShopkeeperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 游弋
 * @create 2023-08-05 6:13
 */
@Service
@RequiredArgsConstructor
public class ShopkeeperImpl extends ServiceImpl<ShopkeeperMapper, Shopkeeper> implements ShopkeeperService {
    private final ShopkeeperMapper shopkeeperMapper;

    @Override
    public Shopkeeper Login(Shopkeeper shopkeeper) {
        return shopkeeperMapper.selectOne(new QueryWrapper<Shopkeeper>().eq("shopkeeper_name", shopkeeper.getShopkeeperName()).eq("shopkeeper_password", shopkeeper.getShopkeeperPassword()));
    }

    @Override
    public Shopkeeper getById(Long shopkeeperId) {
        return shopkeeperMapper.selectById(shopkeeperId);
    }

    @Override
    public Page<Shopkeeper> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<Shopkeeper> shopkeeperPage = new Page<>(pageNo, pageSize);
        return shopkeeperMapper.selectPage(shopkeeperPage, new QueryWrapper<Shopkeeper>().like("shopkeeper_name", keyword));
    }

    @Override
    public boolean addShopkeeper(Shopkeeper shopkeeper) {
        return shopkeeperMapper.insert(shopkeeper) > 0;
    }

    @Override
    public boolean deleteShopkeeper(Long shopkeeperId) {
        return shopkeeperMapper.deleteById(shopkeeperId) > 0;
    }

    @Override
    public boolean updateShopkeeper(Shopkeeper shopkeeper) {
        return shopkeeperMapper.updateById(shopkeeper) > 0;
    }
}
