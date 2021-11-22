package com.answer.demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 * @author answer
 * @version 1.0.0
 * @date 2021/11/17 4:59 下午
 */
public class Producer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //工厂ip 连接RabbitMq队列
        connectionFactory.setHost("82.157.75.90");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        //创建连接
        try {
            Connection connection = connectionFactory.newConnection();
            //连接里边有信道 实际上通信是用 连接里的信道传输的
            Channel channel = connection.createChannel();
            /*
              创建队列 声明 一个队列
              参数： 1 队列名称 2是否需要保存消息（持久化） 默认 消息存储内存中
              3 该队列是否只供一个消费者进行消费，是否进行消息共享，true可以多个消费者消息 false只能一个消费者消息
              4 是否自动删除 最后一个消费者连接后 该队列是否自动删除 true自动删除 false不删除
              5 其他参数
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发消息
            String message = "hello rabbit mq";
            /*
              参数 ：
               1 发送到那个交换机
               2 路由的key值是那个，本次是队列的名称
               3 其他参数
               4 消息体
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("********消费发送完毕*********");
            //关闭连接和通道
            channel.close();
            connection.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
