package com.answer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布订阅高级配置
 * 1 生产者 发送消息 到 broker ，如果中间mq server 挂了如何处理
 * @author answer
 * @version 1.0.0
 * @date 2021/11/25 4:07 下午
 */
@Configuration
public class ConfirmConfig {

    public  static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";

    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";

    public static final String CONFIRM_ROUTING_KEY = " key1";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding confirmBinding(@Qualifier("confirmQueue") Queue confirmQueue,
                                  @Qualifier("confirmExchange") DirectExchange confirmExchange){


        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
}
