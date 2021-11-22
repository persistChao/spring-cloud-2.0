package com.answer.ack;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/18 8:05 下午
 */
public class Worker02 {

    private static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //设置不公平
//        channel.basicQos(1);
        int prefetchCount =2;
        channel.basicQos(prefetchCount);
        System.out.println("C1等待接收消息处理时间较短...");
        DeliverCallback deliverCallback = (consumerTag , message)->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接收到消息==> " + new String(message.getBody(), StandardCharsets.UTF_8));
            /*
              手动应答
              1 消息的标记 tag
              2 是否批量应答 false不批量应答信道中的消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag + " 消费者取消消费接口回调逻辑");
        };
        //采用手动应答 ack
        channel.basicConsume(QUEUE_NAME, false, deliverCallback,cancelCallback);

    }
}
