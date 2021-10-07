package com.answer.controller;

import com.answer.pojo.Order;
import com.answer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource
    private OrderService orderService;

    @GetMapping("{id}")
    public Order getPort(@PathVariable("id") Long id){
        return orderService.queryOrderById(id);
    }
}
