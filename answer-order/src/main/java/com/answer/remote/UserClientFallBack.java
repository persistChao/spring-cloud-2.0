package com.answer.remote;

import com.answer.common.Result;
import com.answer.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/8 4:05 下午
 */
@Slf4j
@Component
public class UserClientFallBack implements UserClient{

    @Override
    public Result<User> findById(Long id) {
        log.info("ERROR : 调用userService异常" );
        return new Result<>(4444,"服务降级返回新建User对象",new User(id, null, null)) ;
    }
}
