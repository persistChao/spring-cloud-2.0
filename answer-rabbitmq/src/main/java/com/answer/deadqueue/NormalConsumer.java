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
 * 死信的来源
 * 1 过期ttl 1000
 * 2 设置队列接收最大的消息数量 如设置x-max-length = 6 则队列只能接收6个 多余的会发送的死信队列
 * 3 消费被拒绝 会发送到死信队列中
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
       // 第一种方式 ttl 消费的过期时间 如消费发送后10s 没有消费 则发送到死信队列 可以在生产者和消费者两种方式设置
        //设置过期时间
//        arguments.put("x-message-ttl", 10000);
        //设置正常队列的死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        arguments.put("x-dead-letter-routing-key", "dead");

        //第2中方式 设置队列最大接收消息数量
        //设置队列最多能接收多少消息 超出的会发送到死信队列中
        arguments.put("x-max-length", 6);
        //声明正常队列
        channel.queueDeclare(NORMAL_QUEUE_NAME, false, false, false, arguments);

        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);

        //绑定交换机和队列
        channel.queueBind(NORMAL_QUEUE_NAME, NORMAL_EXCHANGE_NAME, "normal");

        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "dead");

        System.out.println("等待接收消息......");

        //第3中方式 消息被拒绝 发送到死信队列
//        DeliverCallback deliverCallback = (consumerTag, message) -> {
//            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
//            if (msg.equals("info-5")){
//                System.out.println("Consumer-1接收消息是=>" + msg + " : 此消息是被拒绝的");
//                //requeue 是否放回原队列 即普通队列，如果放回（true）就不会放到死信队列，
//                channel.basicReject(message.getEnvelope().getDeliveryTag(), false );
//            }else {
//                System.out.println("consumer-1 接收消息==>" + new String(message.getBody(), StandardCharsets.UTF_8));
//                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
//            }
//        };

        DeliverCallback deliverCallback = (consumerTag, message) -> System.out.println("consumer-1 接收消息==>" + new String(message.getBody(), StandardCharsets.UTF_8));

            channel.basicConsume(NORMAL_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });


    }
}
