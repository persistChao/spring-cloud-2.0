package com.answer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/30 11:24 上午
 */
@RestController
public class FlowLimitController {
    Logger logger = LoggerFactory.getLogger(FlowLimitController.class);
    @GetMapping("/a")
    public String testA(){
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        logger.info(">>>>>>>>>>>>>>>>/a 被访问");
        return "------test A-------";
    }

    @GetMapping("/b")
    public String testB(){
        logger.info(">>>>>>>>>>>>>>>>/b 被访问");
        return "------test B-------";
    }


    @GetMapping("/testD")
    public String testD(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(">>>>>>>>>>>>>>>>testD 测试RT");
        return "------testD 测试RT-------";
    }

    @GetMapping("/c")
    public String testC(){
        int x = 10/0;
        logger.info(">>>>>>>>>>>>>>>>testC<<<< 测试异常比例");
        return "------testD 测试异常比例-------";
    }
}
