package com.answer.controller;

import com.answer.feign.ZkServerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/29 2:17 下午
 */

@RestController
@RequestMapping("/cum")
@Slf4j
public class ConsumerController {

    @Resource
    private ZkServerClient zkServerClient;

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String zkClientCustomer(){
        return "ZK Client Customer port=" + port;
    }


    @GetMapping("/server/port")
    public String getServerPort(){
        log.info("this method is getServerPort()");
        String serverPort = zkServerClient.getServerPort();
        log.info("------------******************result == {}" ,serverPort);
        return serverPort;
    }


}
