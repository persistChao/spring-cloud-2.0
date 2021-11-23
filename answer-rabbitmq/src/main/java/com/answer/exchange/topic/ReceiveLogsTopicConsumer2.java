package com.answer.exchange.topic;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * 主题交换机消费者
 *
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 10:50 上午
 */
public class ReceiveLogsTopicConsumer2 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //声明队列
        String queueName = "Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
        System.out.println("Q1等待接收消息......");
        DeliverCallback deliverCallback = (consumerTag , message)->{
            System.out.println("接收到的消息=>" + new String(message.getBody(), StandardCharsets.UTF_8));
            System.out.println("接收队列=>" + queueName  + " 绑定建=>" +message.getEnvelope().getRoutingKey() );
        };
        //接收消息
        channel.basicConsume(queueName, true, deliverCallback,consumerTag->{});
    }
}
