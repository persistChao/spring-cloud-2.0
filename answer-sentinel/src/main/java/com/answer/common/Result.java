package com.answer.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/7 4:02 下午
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private int code;

    private T data;

    private String message;

    public Result(int code , String message){
        this.code = code;
        this.message = message;
    }

}
