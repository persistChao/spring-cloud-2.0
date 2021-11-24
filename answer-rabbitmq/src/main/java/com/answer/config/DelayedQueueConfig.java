package com.answer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列 使用插件 rabbitmq_delayed_message_exchange
 * @author answer
 * @version 1.0.0
 * @date 2021/11/24 8:49 下午
 */
@Configuration
public class DelayedQueueConfig {

    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    @Bean
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAYED_QUEUE_NAME).build();
    }

    /**
     * 自定义交换机
     * @return
     */
    @Bean
    public CustomExchange delayedExchange(){
        /*
         1 交换机的名称
         2 交换机类型
         3 是否需要持久化
         4 是否自动删除
         5 其他参数
         */

        Map<String, Object> arguments =  new HashMap<>(4);
        arguments.put("x-delayed-type", "direct");

        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message",
                true,false,arguments);
    }


    @Bean
    public Binding delayedQueueBinding(@Qualifier("delayedQueue") Queue delayedQueue , @Qualifier("delayedExchange") CustomExchange delayedExchange){

        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();

    }
}
