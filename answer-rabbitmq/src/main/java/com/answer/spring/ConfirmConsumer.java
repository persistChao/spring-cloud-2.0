package com.answer.spring;

import com.answer.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/25 4:17 下午
 */
@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveData(Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("Consumer接收队列为confirm_queue的消息为:{}", msg);
    }
}
