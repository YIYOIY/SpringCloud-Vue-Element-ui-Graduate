package com.yoi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Image;
import com.yoi.entity.Shopkeeper;
import com.yoi.mapper.ImageMapper;
import com.yoi.mapper.ShopkeeperMapper;
import com.yoi.service.ShopkeeperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author 游弋
 * @create 2023-08-05 6:13
 */
@Service
@RequiredArgsConstructor
public class ShopkeeperImpl extends ServiceImpl<ShopkeeperMapper, Shopkeeper> implements ShopkeeperService {
    private final ShopkeeperMapper shopkeeperMapper;
    private final ImageMapper imageMapper;

    @Override
    public Shopkeeper getById(Long shopkeeperId) {
        Shopkeeper shopkeeper = shopkeeperMapper.selectById(shopkeeperId);
//        根据id取出照片并放入shopkeeper的image对象中
        Image image = imageMapper.selectById(shopkeeper.getImageId());
        if (!ObjectUtils.isEmpty(image)) {
            shopkeeper.setImage(image);
        }
        return shopkeeper;
    }

    @Override
    public Shopkeeper Login(Shopkeeper shopkeeper) {
        //        虽然是手机号和账户名都可以登录，但前端传递过来是用账户名的字段，所以只要账户名匹配任何一个都可以登录就行了
        return shopkeeperMapper.selectOne(new QueryWrapper<Shopkeeper>().eq("shopkeeper_name", shopkeeper.getShopkeeperName()).eq("shopkeeper_password", shopkeeper.getShopkeeperPassword()));
    }

    @Override
    public Page<Shopkeeper> getAll(String keyword, Integer pageNo, Integer pageSize) {
        Page<Shopkeeper> shopkeeperPage = new Page<>(pageNo, pageSize);
        Page<Shopkeeper> shopkeepers = shopkeeperMapper.selectPage(shopkeeperPage, new QueryWrapper<Shopkeeper>().like("shopkeeper_name", keyword));
        for (Shopkeeper record : shopkeepers.getRecords()) {
            Image image = imageMapper.selectById(record.getImageId());
            if (!ObjectUtils.isEmpty(image)) {
                record.setImage(image);
            }
        }
        return shopkeepers;
    }

    @Override
    public boolean addShopkeeper(Shopkeeper shopkeeper) {
        if (!ObjectUtils.isEmpty(shopkeeper.getImage())) {
            String picture = shopkeeper.getImage().getPicture();
            //            用户没有传递图片时，前端会自动传递一个图片对象， 对象中的图片地址是“”，会导致数据库默认的404图片被“”取代
            if ("".equals(picture)){
                picture=null;
            }
            Image image = new Image(null, picture, null, null, null);
            if (imageMapper.insert(image) < 0) {
                return false;
            } else {
                shopkeeper.setImageId(image.getId());
            }
        }

        return shopkeeperMapper.insert(shopkeeper) > 0;
    }

    @Override
    public boolean deleteShopkeeper(Shopkeeper shopkeeper) {
        imageMapper.deleteById(shopkeeperMapper.selectById(shopkeeper.getId()).getImageId());
        return shopkeeperMapper.deleteById(shopkeeper.getId()) > 0;
    }

    @Override
    public boolean updateShopkeeper(Shopkeeper shopkeeper) {
        if (!ObjectUtils.isEmpty(shopkeeper.getImage())) {
//            如果用户创建时没有上传头像，更新时检测到上传就创建一个图像
            if (shopkeeper.getImage().getId() == null) {
                Image image = new Image(null, shopkeeper.getImage().getPicture(), null, null, null);
                if (imageMapper.insert(image) > 0) {
                    shopkeeper.setImageId(image.getId());
                }else {
                    return false;
                }
            }else {
//                用户创建时已经上传头像，更新时只需要更新头像
                Image image = new Image(shopkeeper.getImageId(), shopkeeper.getImage().getPicture(), null, null, null);
                if (imageMapper.updateById(image) < 0) {
                    return false;
                }
            }
        }

        return shopkeeperMapper.updateById(shopkeeper) > 0;
    }
}
