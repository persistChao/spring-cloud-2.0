package com.answer.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 连接工厂工具类
 * @author answer
 * @version 1.0.0
 * @date 2021/11/18 5:22 下午
 */
public class RabbitMqUtils {
    private static ConnectionFactory factory;
    static {
        factory= new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
    }

    public static Channel getChannel() throws Exception{
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    public static void closeConnection( Channel channel) throws IOException, TimeoutException {
        channel.close();
        channel.getConnection().close();
    }
}
