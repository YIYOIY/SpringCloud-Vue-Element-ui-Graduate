package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Bag;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-04-02 7:14 PM
 */
public interface BagService extends IService<Bag> {
    List<Bag> userGetAll(Integer userId, Integer page);
    //    long getTotal(Connection connection, Integer userId);
    List<Bag> adminGetAll(Integer page);

    boolean addBag(Bag bag);
    boolean deleteBag(Integer bagId);
    //    boolean updateBag(Connection connection,entity.Bag bag);
    Long adminGetCount();
    Long userGetCount(Integer userId);
}
