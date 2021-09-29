package com.answer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/29 2:17 下午
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String zkServer(){
        return "ZK Server Provider port=" + port + new Date();
    }
}
