package com.answer.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/11/25 5:11 下午
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnCallback{

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 注入 将当前的MyCallBack注入到 RabbitTemplate 的confirmCallback
     */
    @PostConstruct
    private void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机回调确认
     * 1 发消息 交换机接收到了 回调
     *  1.1 correlationData 保存了回调消息的ID及相关信息
     *  1.2 交换机收到消息 ack=true
     *  1.3 cause null
     * 2 发消息 交换机接收失败 回调
     *  2.1 correlationData 保存了回调消息的ID及相关信息
     *  2.2 交换机收到消息 ack=false
     *  2.3 cause 回调失败原因
     *
     * @param correlationData 关联数据
     * @param ack             消
     * @param cause           导致
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机收到了Id={}的消息",id);
        }else {
            log.info("交换机收到了Id={}的消息 原因 cause={}",id,cause);
        }
    }


    /**
     * 默认情况 当生产者 发送消息到broker 交换机确认成功 ，
     * 但是 在交换机路由到队列的时候 找不到队列会直接丢弃消息
     * 这里是设置交换机路由不到队列时返回生产者 可以继续发送
     * 返回消息
     *
     * @param message    消息
     * @param replyCode  回复代码
     * @param replyText  回复文本
     * @param exchange   交换
     * @param routingKey 路由的关键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息{},被交换机{}退回，退回原因:{} 路由key:{}",new String(message.getBody()) ,exchange,replyText,routingKey);
    }
}
