package com.answer.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.answer.common.Result;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/7 4:23 下午
 */
public class GlobalHandler {

    public static Result handlerException1(BlockException exception){
        return new Result(4441, "按客户自定义 global handlerException1");
    }

    public static Result handlerException2(BlockException exception){
        return new Result(4442, "按客户自定义 global handlerException2");
    }
}
