package com.yoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 游弋
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
public class ImgMain {
    public static void main(String[] args) {
        SpringApplication.run(ImgMain.class,args);
    }
}