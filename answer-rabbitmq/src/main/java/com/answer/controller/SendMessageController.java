package com.answer.controller;

import cn.hutool.core.date.DateUtil;
import com.answer.config.DelayedQueueConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 发送延迟消息
 *
 * @author answer
 * @version 1.0.0
 * @date 2021/11/23 5:26 下午
 */
@Api(tags = "发送消息-生产者")
@Slf4j
@RestController
@RequestMapping("/msg")
public class SendMessageController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("发送普通消息")
    @GetMapping("/ttl/sendMsg/{msg}")
    public String sendMsg(@PathVariable("msg") String msg) {
        log.info("当前时间:{},发送一条信息给两个TTL队列: {} ", DateUtil.formatDateTime(new Date()), msg);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl10s的队列:" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl10s的队列:" + msg);

        return "ok";
    }

    @ApiOperation("发送ttl延迟的消息")
    @GetMapping("/send/{msg}/{ttlTime}")
    public String sendMsg(@PathVariable("msg") String msg, @PathVariable("ttlTime") String ttlTime) {
        log.info("当前时间:{},发送消息时长{}毫秒TTL信息给队列QC:{}", DateUtil.formatDateTime(new Date()), ttlTime, msg);
        rabbitTemplate.convertAndSend("X", "XC", msg.getBytes(StandardCharsets.UTF_8), m -> {
            /*
                在消息发送时 在消息上设置ttl 消息可能并不会按时延迟（按时死亡） 因为Rabbitmq只会检查第一个消息是否过期，如果过期则会丢到死信队列，
                如果第一个消息的延迟时长很长，而第二个消息的延迟时长很短，第二个消息并不会有限执行
             */
            //发送消息的时候 延迟时长
            m.getMessageProperties().setExpiration(ttlTime);
            return m;
        });
        return "success";
    }

    @ApiOperation("发送delayed的延迟消息")
    @GetMapping("/send/delayed/{msg}/{delayedTime}")
    public String sendDelayedMsg(@PathVariable("msg") String msg ,
                                 @PathVariable("delayedTime") Integer delayedTime){
        log.info("当前时间={} 发送一条时长{}毫秒信息给延迟队列delayed.queue:{}",DateUtil.formatDateTime(new Date()) ,delayedTime,msg);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY ,msg ,m->{
            m.getMessageProperties().setDelay(delayedTime);
            return m;
        });

        return "success";
    }
}
