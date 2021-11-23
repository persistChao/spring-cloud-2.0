package com.answer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * ttl 配置
 *
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 5:00 下午
 */
@Configuration
public class TtlQueueConfig {
    /**
     * 普通交换机名称
     */
    private static final String X_EXCHANGE = "X";

    /**
     * 普通队列名称
     */
    private static final String QUEUE_A = "QA";
    /**
     * 死信交换机名称
     */
    private static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    /**
     * 队列B名称
     */
    private static final String QUEUE_B = "QB";

    /**
     * 死信队列名称
     */
    private static final String DEAD_QUEUE = "QD";

    /**
     * 声明xExchange
     */
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 声明yExchange
     */
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明普通队列
     */
    @Bean
    public Queue queueA(){
        Map<String, Object> arguments = new HashMap<>(4);
        //设置消费消息的过期时间ttl 单位ms
        arguments.put("x-message-ttl", 10000);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信队列
        arguments.put("x-dead-letter-routing-key", "YD");

        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    /**
     * 声明队列B
     */
    @Bean("queueB")
    public Queue queue(){
        Map<String, Object> arguments = new HashMap<>(4);
        //设置消费消息的过期时间ttl 单位ms
        arguments.put("x-message-ttl", 40000);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信队列
        arguments.put("x-dead-letter-routing-key", "YD");

        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }


    @Bean("queueD")
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    //=====================绑定队列和交换机=====================

    @Bean
    public Binding queueABindExchangeX(@Qualifier("queueA") Queue queueA ,
                                       @Qualifier("xExchange") DirectExchange xExchange){

        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindExchangeX(@Qualifier("queueB") Queue queueB ,
                                       @Qualifier("xExchange") DirectExchange xExchange){

        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueDBindExchangeY(@Qualifier("queueD") Queue queueD ,
                                       @Qualifier("yExchange") DirectExchange yExchange){

        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
