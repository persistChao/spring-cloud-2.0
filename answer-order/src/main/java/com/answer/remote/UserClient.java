package com.answer.remote;

import com.answer.common.Result;
import com.answer.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/7 5:52 下午
 */
@FeignClient(value = "answer-user")
public interface UserClient {
    @GetMapping("/user/{id}")
    Result<User> findById(@PathVariable("id")  Long id);
}
