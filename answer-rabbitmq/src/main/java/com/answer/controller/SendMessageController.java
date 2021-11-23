package com.answer.controller;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 发送延迟消息
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 5:26 下午
 */
@Slf4j
@RestController
@RequestMapping("/msg")
public class SendMessageController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/ttl/sendMsg/{msg}")
    public String sendMsg(@PathVariable("msg") String msg){
        log.info("当前时间:{},发送一条信息给两个TTL队列: {} ", DateUtil.formatDateTime(new Date()) ,msg);

        rabbitTemplate.convertAndSend("X", "XA","消息来自ttl10s的队列:" + msg);
        rabbitTemplate.convertAndSend("X", "XB","消息来自ttl10s的队列:" + msg);

        return "ok";
    }
}
