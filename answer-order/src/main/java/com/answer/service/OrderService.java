package com.answer.service;

import com.answer.common.Result;
import com.answer.mapper.OrderMapper;
import com.answer.pojo.Order;
import com.answer.pojo.User;
import com.answer.remote.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.用Feign远程调用
        Result<User> result  = userClient.findById(order.getUserId());
        // 3.封装user到Order
        order.setUser(result.getData());
        // 4.返回
        return order;
    }

    public Result<User> getUserById(Long id){
        Result<User> result = userClient.findById(id);
        if (id==4){
            throw new IllegalArgumentException("IllegalArgumentException，非法参数异常....");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException ,该ID没有对应记录，空指针异常...");
        }
        return result;
    }

}
