package com.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author 游弋
 * @create 2023-06-07 3:43 PM
 */
@Controller
public class img {

    private String picturePath;

//    @GetMapping("*.img")
//    public String test(String name) throws FileNotFoundException {
//        Path path = Paths.get("C:\\Users\\yo\\Desktop\\vuespringbootTotalProject\\WebDevelopment\\back\\demo\\src\\main\\resources\\static\\img");
//        String path2=path.toString().replaceAll("\\\\","/")+"/";
//        path2=path2.substring(path2.lastIndexOf("/img"));
//        return path2+name;
//    }

    @RequestMapping("/test/up")
    public ResponseEntity<String> testUp(HttpSession session, MultipartFile photo) throws IOException {
//        获取上传的文件名
        String filename = photo.getOriginalFilename();

//        以下操作是为了保证文件名不重复，文件名重复会导致相同文件名的内容被覆盖
//        获取上传的文件后缀名
        String hzname = filename.substring(filename.lastIndexOf("."));
//        获取uuid,也可以用获取时间戳的方式
        String s = UUID.randomUUID().toString();
//        拼接一个新文件名
        filename = s + hzname;

        File file = new File("C:/Users/yo/Desktop/vuespringbootTotalProject/WebDevelopment/back/demo/src/main/resources/static/img");
        if (!file.exists()){
            file.mkdir();
        }

        Path path2 = Paths.get("C:\\Users\\yo\\Desktop\\vuespringbootTotalProject\\WebDevelopment\\back\\demo\\src\\main\\resources\\static\\img");

        String finalPath = path2 + File.separator + filename;
        photo.transferTo(new File(finalPath));

//        上传成功后返回成功信息
//        picturePath="api/img/"+filename;
        picturePath="api/"+filename;
        return ResponseEntity.ok(picturePath);
    }


    @RequestMapping("/getPicture")
    private ResponseEntity<String> getPicture(){
        String picture=picturePath;
        System.out.println(picture);
        return ResponseEntity.ok(picture);
    }

    @RequestMapping("/test/download")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        Path path2 = Paths.get("C:\\Users\\yo\\Desktop\\vuespringbootTotalProject\\WebDevelopment\\back\\demo\\src\\main\\resources\\static\\img");
        String name=picturePath.substring(4);
        System.out.println(name);
        System.out.println(path2);
        String finalPath = path2 + File.separator + name;
        System.out.println(finalPath);

//创建输入流
        InputStream is = new FileInputStream(finalPath);
//创建字节数组，is.availiable,获取输入流所对应文件的字节数
        byte[] bytes = new byte[is.available()];
//将流读到字节数组中
        is.read(bytes);

//创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
//设置要下载方式以及下载文件的名字,  content-Disposition,attachment:filename=是固定格式
        headers.add("Content-Disposition", "attachment;filename="+name);
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
