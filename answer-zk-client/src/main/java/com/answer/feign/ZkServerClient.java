package com.answer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/29 4:52 下午
 */
@FeignClient(value = "zk-server")
public interface ZkServerClient {

    @GetMapping("/provider/port")
    String getServerPort();
}
