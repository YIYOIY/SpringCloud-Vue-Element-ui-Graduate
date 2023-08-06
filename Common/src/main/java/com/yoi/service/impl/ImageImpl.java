package com.yoi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoi.entity.Image;
import com.yoi.mapper.ImageMapper;
import com.yoi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 游弋
 * @create 2023-08-05 18:31
 */
@Service
@RequiredArgsConstructor
public class ImageImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    private final ImageMapper imageMapper;
    @Override
    public Boolean addImage(Image image) {
        return imageMapper.insert(image)>0;
    }

    @Override
    public Boolean deleteImage(Long imageId) {
        return imageMapper.deleteById(imageId)>0;
    }

    @Override
    public Image getById(Long imageId) {
        return imageMapper.selectById(imageId);
    }

    @Override
    public Boolean updateImage(Image image) {
        return imageMapper.updateById(image)>0;
    }
}
