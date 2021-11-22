package com.answer.exchange.direct;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * 直接交换机
 * 指定发送到某个队列
 * @author answer
 * @version 1.0.0
 * @date 2021/11/22 8:45 下午
 */
public class DeceiveLogsDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare("console", false, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console", EXCHANGE_NAME, "warming");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("DeceiveLogsDirect接收到日志消息=>" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume("console", true,deliverCallback,consumerTag->{} );


    }
}
