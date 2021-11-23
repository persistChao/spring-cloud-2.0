package com.answer.spring;

import cn.hutool.core.date.DateUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *  死信队列消费
 *
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 5:26 下午
 */
@Slf4j
@Component
public class DeadLetterConsumer {

    @RabbitListener(queues = "QD" )
    public void receiveMessage(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("当前时间=>{} 接收到[死信队列]的消息msg={} ", DateUtil.formatDateTime(new Date()),msg );


    }
}
