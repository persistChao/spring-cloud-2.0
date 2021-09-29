package com.answer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/29 2:15 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZkServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkServerApplication.class, args);
    }
}
