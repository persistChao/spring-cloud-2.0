package com.answer.service;

import com.answer.mapper.UserMapper;
import com.answer.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/7 5:31 下午
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User queryById(Long id) {
        return userMapper.findById(id);
    }
}
