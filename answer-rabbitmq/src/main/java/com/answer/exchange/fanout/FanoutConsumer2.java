package com.answer.exchange.fanout;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * 发布订阅模式
 * 1 交换机将收到的消息广播到他知道的(绑定的)所有队列中
 * @author answer
 * @version 1.0.0
 * @date 2021/11/22 8:25 下午
 */
public class FanoutConsumer2 {
    private static final String EXCHANGE_NAME = "logs";

    private static final String QUEUE_FANOUT_1 = "queue_fanout_1";
    private static final String QUEUE_FANOUT_2 = "queue_fanout_2";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机Exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        /*
          生成一个临时的队列，临时队列在连接断开后就自动删除
         */
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("等待接收消息，把接收消息打印在屏幕上......");
        DeliverCallback deliverCallback = (consumerTag , message)->{
            System.out.println("接收到消息=>" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume(queue, true, deliverCallback,consumerTag->{});

    }
}
