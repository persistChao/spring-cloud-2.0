package com.answer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/29 11:30 上午
 */
@RequestMapping("/order")
@Slf4j
@RestController
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("{id}")
    public String order(@PathVariable("id") Integer id){
        log.info("access order service id={}",id);
        return "order service port:" + serverPort;
    }
}
