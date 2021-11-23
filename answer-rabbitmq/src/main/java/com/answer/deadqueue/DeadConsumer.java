package com.answer.deadqueue;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 2:38 下午
 */
public class DeadConsumer {

    /**
     * 死信交换机名称
     */

    private static final String DEAD_QUEUE_NAME = "dead_queue";


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("死信队列消费者准备接收消息......");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("死信队列消费者 接收消息==>" + new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), true);
        };

        channel.basicConsume(DEAD_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }
}
