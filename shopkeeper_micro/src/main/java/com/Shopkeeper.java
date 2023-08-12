package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 游弋
 * @create 2023-08-07 0:08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
public class Shopkeeper {
    public static void main(String[] args) {
        SpringApplication.run(Shopkeeper.class,args);
    }
}
