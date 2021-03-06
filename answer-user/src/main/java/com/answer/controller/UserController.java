package com.answer.controller;

import com.answer.common.Result;
import com.answer.pojo.User;
import com.answer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author answer
 * @date 2021-10-07
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public Result<User> queryById(@PathVariable("id") Long id,
                                 @RequestHeader(value = "Truth", required = false) String truth) {
        log.info("userService queryById id={}",id);
        User user = userService.queryById(id);
        return Result.ok(user);

    }
}
