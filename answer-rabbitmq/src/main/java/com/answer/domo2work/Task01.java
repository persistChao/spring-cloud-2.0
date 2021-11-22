package com.answer.domo2work;

import com.answer.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/18 7:20 下午
 */
public class Task01 {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //从控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("发送消息完成msg=" + msg);
        }

    }
}
