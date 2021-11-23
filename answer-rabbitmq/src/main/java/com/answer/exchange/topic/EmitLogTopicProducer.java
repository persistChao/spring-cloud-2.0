package com.answer.exchange.topic;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 10:58 上午
 */
public class EmitLogTopicProducer {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        Map<String, String> bindingMap = new HashMap<>();
        bindingMap.put("quick.orange.rabbit", "队列被Q1Q2接收到");
        bindingMap.put("lazy.orange.elephant", "队列被Q1Q2接收到");
        bindingMap.put("quick.orange.fox", "队列被Q1接收到");
        bindingMap.put("lazy.brown.fox", "队列被Q2接收到");
        bindingMap.put("lazy.pink.rabbit", "虽然满足两个绑定单只被Q2接收一次");
        bindingMap.put("quick.brown.fox", "不匹配任何绑定不会被任何队列接收到，会被丢弃");
        bindingMap.put("lazy.orange.male.rabbit", "是四个单词单匹配Q2");
        bindingMap.put("quick.orange.male.rabbit", "是四个单词不匹配任何绑定会被丢弃");


        bindingMap.forEach((key, value) -> {
            try {
                channel.basicPublish(EXCHANGE_NAME, key, null, value.getBytes(StandardCharsets.UTF_8));
                System.out.println("发送消息=====>" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
