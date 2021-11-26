package com.answer.controller;

import com.answer.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/25 4:14 下午
 */
@RequestMapping("/confirm")
@RestController
@Slf4j
public class ProducerController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public String sendMessage(@PathVariable("message") String message){
        CorrelationData correlationData = new CorrelationData("1");
        //测试发送成功的回调
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY, message + "key1", correlationData);
        //测试发送失败的回调 交换机错误的情况
//        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME + "2232", ConfirmConfig.CONFIRM_ROUTING_KEY, message,correlationData);
        log.info("发送消息内容为:{}",message + "key1");

        CorrelationData correlationData2 = new CorrelationData("2");
        // 队列错误的情况 在队列写错的情况下 交换机可以收到消息 但是叫交换机不会发送到queue 消费者不会消费到指定queue的消息
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY +"2", message + "key2",correlationData2);
        log.info("发送消息内容为:{}",message + "key2");

        return "success";
    }
}
