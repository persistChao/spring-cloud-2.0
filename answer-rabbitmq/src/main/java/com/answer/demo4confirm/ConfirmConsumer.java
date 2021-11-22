package com.answer.demo4confirm;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/22 4:13 下午
 */
public class ConfirmConsumer {

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag,  message)->{
            System.out.println("接收消息 msg=" + new String(message.getBody(), StandardCharsets.UTF_8));
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //手动应答ack
            channel.basicAck(message.getEnvelope().getDeliveryTag(),true );
        };
        CancelCallback cancelCallback = consumerTag->{
            System.out.println(consumerTag + "=> 消费者去掉消费回调接口");
        };
        channel.basicConsume("4719b4d8-5284-4132-863c-142ab408de5a", false, deliverCallback, cancelCallback);


    }
}
