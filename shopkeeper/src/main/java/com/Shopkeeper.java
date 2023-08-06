package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 游弋
 * @create 2023-08-07 0:08
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Shopkeeper {
    public static void main(String[] args) {
        SpringApplication.run(Shopkeeper.class,args);
    }
}
