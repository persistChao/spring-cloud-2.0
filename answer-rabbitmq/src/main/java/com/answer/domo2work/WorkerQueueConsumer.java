package com.answer.domo2work;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/18 5:28 下午
 */
public class WorkerQueueConsumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag , message)->{
            System.out.println("接收到的消息=> " + new String(message.getBody()) + " 当前线程=>" + Thread.currentThread().getName());
        };

        CancelCallback cancelCallback = consumerTag->{
            System.out.println(consumerTag+" 消费者取消消费接口回调");
        };
        System.out.println("C2等待接收消息...");
        //接收消息
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);

    }
}
