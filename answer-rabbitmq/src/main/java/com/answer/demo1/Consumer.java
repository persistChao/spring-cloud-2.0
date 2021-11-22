package com.answer.demo1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/17 4:59 下午
 */
public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("82.157.75.90");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //声明 接收消息
            DeliverCallback deliverCallback = ( consumerTag,  message)->{
                System.out.println("消费到的消息=>" + new String(message.getBody()));
            };
            CancelCallback cancelCallback =(consumerTag)->{
                System.out.println("消息消费被中断 tag=" + consumerTag.toString());
            };
            /**
             * 消费者消息消息
             * 参数
             * 1 消费那个队列
             * 2 消费成功后是否要自动应答 ack true 代表自动 false代表手动
             * 3 消费者未成功消费的回调
             * 4 消费者取消消费的回调
             */
            channel.basicConsume(QUEUE_NAME, true,deliverCallback,cancelCallback);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
