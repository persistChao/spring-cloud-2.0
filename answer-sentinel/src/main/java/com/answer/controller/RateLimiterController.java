package com.answer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.answer.common.Result;
import com.answer.handler.GlobalHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/7 4:01 下午
 */
@RestController
@RequestMapping("/rateLimit")
@Slf4j
public class RateLimiterController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler ="handlerException")
    public Result byResource(){

        log.info("按资源名称限流OK");
        return new Result(200,"按资源名称限流OK");
    }

    public Result handlerException(BlockException e){
        log.info("异常处理");
        return new Result(444, e.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl")
    public Result byUrl(){

        log.info("按url称限流OK");
        return new Result(200,"按url限流OK");
    }

    @GetMapping("customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = GlobalHandler.class,blockHandler = "handlerException2")
    public Result globalException(){
        return new Result(200, "按客户自定义请求成功");
    }
}
