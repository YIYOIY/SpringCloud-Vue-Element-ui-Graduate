package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author 游弋
 * @create 2023-07-19 12:37
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class User {
    public static void main(String[] args) {
        SpringApplication.run(User.class,args);
    }
}