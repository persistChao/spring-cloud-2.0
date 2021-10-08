package com.answer.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/10/7 4:02 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;

    private String message;
    private T data;


    public Result(int code , String message){
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> ok(T data){
        return new Result<>(200,"success",data);
    }

}
