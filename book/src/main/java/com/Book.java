package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 游弋
 * @create 2023-07-19 12:37
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Book {
    public static void main(String[] args) {
        SpringApplication.run(Book.class,args);
    }
}