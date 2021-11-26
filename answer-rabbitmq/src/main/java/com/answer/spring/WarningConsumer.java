package com.answer.spring;

import com.answer.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 备份交换机 报警消息接收
 * @author answer
 * @version 1.0.0
 * @date 2021/11/26 4:46 下午
 */
@Component
@Slf4j
public class WarningConsumer {


    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("报警发现不可路由的消息:{}",msg);
    }
}
