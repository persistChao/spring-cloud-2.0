package com.answer.spring;

import cn.hutool.core.date.DateUtil;
import com.answer.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/25 10:50 上午
 */
@Slf4j
@Component
public class DelayedConsumer {

    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedData(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("当前时间={} 接收到延迟队列的消息={}", DateUtil.formatDateTime(new Date()),msg);
    }
}
