package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 游弋
 * @create 2023-07-19 11:56
 */
@SpringBootApplication
@MapperScan("com.yoi.mapper")
@EnableRedisHttpSession
public class CommonSupport {
    public static void main(String[] args) {
        SpringApplication.run(CommonSupport.class);
    }
}