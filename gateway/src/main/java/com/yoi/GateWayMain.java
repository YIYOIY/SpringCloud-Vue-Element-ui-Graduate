package com.yoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 游弋
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayMain {
    public static void main(String[] args) {
        SpringApplication.run(GateWayMain.class,args);
    }
}