package com.yoi.controller;

import com.yoi.entity.ReturnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author 游弋
 * @create 2023-06-07 3:43 PM
 */
@RestController
@RefreshScope
@Slf4j
public class img {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private String picturePath;
    //    使用yaml配置对象的方式进行一次设置，持续引用
    @Value("${user.lo}")
    private String FILEPATH;

    @RequestMapping("/test/up/{id}")
    public void testUp(@PathVariable Long id, @RequestPart("photo") MultipartFile photo) throws IOException {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        log.info("上传的信息: photo={}", photo);

        if (!photo.isEmpty()) {
//        获取上传的文件名
            String filename = photo.getOriginalFilename();

//        以下操作是为了保证文件名不重复，文件名重复会导致相同文件名的内容被覆盖
//        获取上传的文件后缀名
            assert filename != null;
            String name = filename.substring(filename.lastIndexOf("."));
//        获取uuid,也可以用获取时间戳的方式
            String s = UUID.randomUUID().toString();
//        拼接一个新文件名
            filename = s + name;
//        如果运行的项目没有初始数据，是一个崭新的项目，可以直接在配置文件中写入图片文件要保存的地址后进行
            File file = new File(FILEPATH);
            if (!file.exists()) {
                System.out.println("正在创建文件夹！");
                boolean mkdirs = file.mkdirs();
                if (!mkdirs) {
                    System.out.println("创建图片文件夹失败");
                }
            }
            FILEPATH = FILEPATH.replaceAll("\\\\", "/");

            System.out.println("已经获得当前项目图片文件夹在当前设备的绝对路径：" + FILEPATH);
            Path path2 = Paths.get(FILEPATH);
            String finalPath = path2 + File.separator + filename;

            photo.transferTo(new File(finalPath));
//          上传成功后保存图片路径
            picturePath = "img/" + filename;
            System.out.println("保存在redis的图片请求路径" + picturePath);
        }
        //图片路径放入redis
        stringStringValueOperations.set(id.toString(), picturePath);
    }


    @RequestMapping("/getPicture/{id}")
    public ReturnInfo<String> getPicture(@PathVariable Long id) {
//        从redis中读取图片路径的数据
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String s = stringStringValueOperations.get(id.toString());
        if (s != null) {
            return new ReturnInfo<>(200, "图片路径获取完成！", s);
        } else {
            return new ReturnInfo<>(404, "图片路径获取失败！");
        }
    }


    @RequestMapping("/test/download")
    public ResponseEntity<byte[]> testResponseEntity() throws IOException {
//        Path path2 = Paths.get("C:\\Users\\yo\\Desktop\\vuespringbootTotalProject\\WebDevelopment\\back\\demo\\src\\main\\resources\\static\\img");
        Path path2 = Paths.get(FILEPATH);
//        因为返回去的 api/，正好4个可以截断api/
        String name = picturePath.substring(4);
        System.out.println(name + "图片下载的图片名称");
        System.out.println(path2 + "将要在这个地址进行下载");
        String finalPath = path2 + File.separator + name;
        System.out.println(finalPath + "最终下载路径以及图片名");

//创建输入流
//      InputStream is = new FileInputStream(finalPath);
        InputStream is = Files.newInputStream(Paths.get(finalPath));
//创建字节数组，is.available,获取输入流所对应文件的字节数
        byte[] bytes = new byte[is.available()];
//将流读到字节数组中
        int read = is.read(bytes);

//创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
//设置要下载方式以及下载文件的名字,  content-Disposition,attachment:filename=是固定格式
        headers.add("Content-Disposition", "attachment;filename=" + name);
//设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
//创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers,
                statusCode);
//关闭输入流
        is.close();
        return responseEntity;
    }
}
