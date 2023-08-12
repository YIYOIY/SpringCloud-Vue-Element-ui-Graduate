package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 游弋
 * @create 2023-07-19 12:37
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
public class Admin {
    public static void main(String[] args) {
        SpringApplication.run(Admin.class,args);
    }
}