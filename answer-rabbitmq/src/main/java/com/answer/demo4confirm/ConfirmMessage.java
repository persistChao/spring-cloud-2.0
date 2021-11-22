package com.answer.demo4confirm;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 发布确认模式
 * 比较实用的时间 比较那种确认方式是最好的
 * 1 单个确认
 * 2 批量确认
 * 3 异步确认
 *
 * @author answer
 * @version 1.0.0
 * @date 2021/11/22 10:50 上午
 */
public class ConfirmMessage {

    //批量发布消息个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        //1 单个确认 发布1000个耗时 7000ms
//        publishMessageIndividually();

        //2 批量确认 发布1000个耗时 146ms
//        publishMessageBatch();

        //3 异步确认 发布1000个耗时  26ms
        publishMessageAsync();
    }

    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列的名称
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long start = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            //单个消息马上就进行发布确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息发送成功=>" + i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("单个确认发布耗时" + (end - start) + "ms");
        RabbitMqUtils.closeConnection(channel);
    }

    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列的名称
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long start = System.currentTimeMillis();
        //批量确认消息的大小
        int batchSize = 100;

        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());

        }
        long end = System.currentTimeMillis();
        System.out.println("批量确认发布耗时" + (end - start) + "ms");
        RabbitMqUtils.closeConnection(channel);
    }


    /**
     * 异步发布确认
     */
    public static void publishMessageAsync() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //队列的名称
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        // 拿到未确认消息的方案 放到map中吧已确认的 和 所有的 ，未确认的就是 所有的减去已确认的
        /*
          线程安全有序的哈希表 适用于高并发的情况下
          功能
          1 将序号与消息进行关联
          2 批量删除条目 只要给序号
          3 支持高并发访问（多线程）
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirms =
                new ConcurrentSkipListMap<>();

        //声明发布确认的监听器
        ConfirmCallback ackCallback = ( deliveryTag,  multiple)->{
            //如果是批量确认
            if (multiple){
                //2 删除已确认的消息 剩下的就是未确认的消息
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            }else {
                //一般使用单个确认
                outstandingConfirms.remove(deliveryTag);
            }
            System.out.println("确认的消息=>" + deliveryTag);

        };
        ConfirmCallback nackCallback = (deliveryTag,  multiple)->{
            // 3打印未确认的消息
            String message = outstandingConfirms.get(deliveryTag);
            System.out.println("未确认的消息是=>" + message + "====== 未确认的消息tag=>" + deliveryTag);
        };
        channel.addConfirmListener(ackCallback,nackCallback);
        //开始时间
        long start = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "消息" + i ;
            channel.basicPublish("", queueName, null, message.getBytes());
            // 1 记录下所有要发送的消息
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }
        long end = System.currentTimeMillis();
        System.out.println("[异步]确认发布耗时" + (end - start) + "ms");
        //异步发布确认 这里必须去掉
//        RabbitMqUtils.closeConnection(channel);
    }

}
