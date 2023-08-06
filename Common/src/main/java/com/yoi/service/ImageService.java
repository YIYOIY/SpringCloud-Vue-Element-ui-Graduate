package com.yoi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoi.entity.Image;

/**
 * @author 游弋
 * @create 2023-08-05 18:32
 */
public interface ImageService extends IService<Image> {
    Boolean addImage(Image image);

    Boolean deleteImage(Long imageId);

    Image getById(Long imageId);

    Boolean updateImage(Image image);
}
