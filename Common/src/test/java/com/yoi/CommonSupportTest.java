package com.yoi;

import com.yoi.entity.Image;
import com.yoi.mapper.ImageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 游弋
 * @create 2023-08-05 2:23
 */
@SpringBootTest
public class CommonSupportTest {
    @Autowired
    public ImageMapper imageMapper;

    @Test
    public void test() {
        Image img = new Image();
        img.setId(123L);
        img.setPicture("wuhu");
        imageMapper.insert(img);
//        Image byId = imageService.getById(img.getId());
//        System.out.println(byId.getPicture());

//        Admin admin = new Admin(null,img.getId(),"admin","admin",
//                "123456", SexEnum.MALE, LocalDate.now(),null,
//                "admin","12345678",10.0);
//        int insert = adminMapper.insert(admin);
//        try {
//            boolean b = adminService.addAdmin(admin);
//            System.out.println(adminMapper.selectById(admin.getId()));
//        }catch (DataAccessException e){
//            System.out.println("失误");
//        }

//        Page<Admin> all = adminService.getAll("", 1, 5);
//        System.out.println(all.toString());
    }
}
