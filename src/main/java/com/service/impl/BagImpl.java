package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Bag;
import com.mapper.BagMapper;
import com.service.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:19 PM
 */
@Service
public class BagImpl extends ServiceImpl<BagMapper,Bag> implements BagService {
    @Autowired
    BagMapper bagMapper;
    @Override
    public List<Bag> userGetAll(Integer userId, Integer page) {
        int pageStart=(page-1)*5;
        return bagMapper.userGetAll(userId, pageStart);
    }

    @Override
    public List<Bag> adminGetAll(Integer page) {
        int pageStart=(page-1)*5;
        return bagMapper.adminGetAll(pageStart);
    }

    @Override
    public boolean addBag(Bag bag) {
        return bagMapper.addBag(bag);
    }

    @Override
    public boolean deleteBag(Integer bagId) {
        return bagMapper.deleteBag(bagId);
    }

    @Override
    public Long adminGetCount() {
        return bagMapper.adminGetCount();
    }

    @Override
    public Long userGetCount(Integer userId) {
        return bagMapper.userGetCount(userId);
    }
}
