package com.answer.deadqueue;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * 死信队列之生产者
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 2:25 下午
 */
public class DeadQueueProducer {

    /**
     * 普通交换机的名称
     */
    private static final String NORMAL_EXCHANGE_NAME = "normal_exchange";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
//        //死信消息 设置TTL 过期时间单位是毫秒 ms
//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
//                .expiration("10000").build();
        for (int i = 1; i < 11; i++) {
            String message = "info-" + i;
            channel.basicPublish(NORMAL_EXCHANGE_NAME, "normal", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息 ==> "+message);
        }
    }
}
