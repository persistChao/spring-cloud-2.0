package com.answer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/29 2:15 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@Configuration
public class ZkClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
