package com.answer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.answer.common.Result;
import com.answer.pojo.Order;
import com.answer.pojo.User;
import com.answer.remote.UserClient;
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
    public Result<Order> getPort(@PathVariable("id") Long id){
        return Result.ok(orderService.queryOrderById(id));
    }




    @GetMapping("/user/{id}")

    //@SentinelResource(value = "fallback")//没有配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",fallback = "handlerFallback" ,blockHandler = "blockHandler",exceptionsToIgnore = {IllegalArgumentException.class})//排除忽略某个异常
    public Result<User> getUser(@PathVariable("id") Long id) {
        return orderService.getUserById(id);
    }

    public Result<User> blockHandler(@PathVariable("id") Long id, BlockException e){
        User user = new User(id, null, null);
        return new Result<>(445, "blockHandler-sentinel限流，exception内容："+ e.getMessage(), user);
    }

    public Result<User> handlerFallback(@PathVariable("id") Long id,Throwable e){
        User user = new User(id, null, null);
        return new Result<>(444, "兜底异常handlerFallback，exception内容："+ e.getMessage(), user);
    }

//    @Resource
//    private UserClient userClient;
//
//    @GetMapping("/consumer/user/{id}")
//    public Result<User> user(@PathVariable("id") Long id){
//        return userClient.findById(id);
//    }
}
