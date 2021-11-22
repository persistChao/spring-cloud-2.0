package com.answer.exchange.direct;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/22 8:37 下午
 */
public class DirectProducer {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            if (msg.contains("info")) {
                channel.basicPublish(EXCHANGE_NAME, "info", null, msg.getBytes(StandardCharsets.UTF_8));
            }else if (msg.contains("error")){
                channel.basicPublish(EXCHANGE_NAME, "error", null, msg.getBytes(StandardCharsets.UTF_8));
            }else {
                channel.basicPublish(EXCHANGE_NAME, "warming", null, msg.getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("生产者发出消息=>" + msg);
        }
    }
}
