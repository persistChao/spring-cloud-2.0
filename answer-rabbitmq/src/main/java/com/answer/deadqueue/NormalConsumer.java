package com.answer.deadqueue;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列 消费者2 -- 正常的消费者
 *
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 11:26 上午
 */
public class NormalConsumer {

    /**
     * 普通交换机的名称
     */
    private static final String NORMAL_EXCHANGE_NAME = "normal_exchange";

    /**
     * 死信交换机名称
     */
    private static final String DEAD_EXCHANGE_NAME = "dead_exchange";

    private static final String DEAD_QUEUE_NAME = "dead_queue";

    private static final String NORMAL_QUEUE_NAME = "normal_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        //声明交换机 死信和普通的交换机 类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //普通队列的参数
        Map<String, Object> arguments = new HashMap<>(4);
        //设置过期时间
        arguments.put("x-message-ttl", 10000);
        //设置正常队列的死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        arguments.put("x-dead-letter-routing-key", "dead");

        //声明正常队列
        channel.queueDeclare(NORMAL_QUEUE_NAME, false, false, false, arguments);

        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);

        //绑定交换机和队列
        channel.queueBind(NORMAL_QUEUE_NAME, NORMAL_EXCHANGE_NAME, "normal");

        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "dead");

        System.out.println("等待接收消息......");

        DeliverCallback deliverCallback = (consumerTag, message) -> System.out.println("consumer-1 接收消息==>" + new String(message.getBody(), StandardCharsets.UTF_8));
        channel.basicConsume(NORMAL_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });


    }
}
