package com.ssmdemo.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * global exception handler
 * Created by zhugc on 2018/4/25-21:39.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handle(Exception e){
        e.printStackTrace();
        return "Exception:"+e.getMessage();
    }
}
