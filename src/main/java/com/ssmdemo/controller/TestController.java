package com.ssmdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhugc on 2018/4/25-21:41.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/a")//checked exception
    public IllegalAccessException exception1() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @RequestMapping("/b")
    //RuntimeException ,non-checked exception
    public ArrayIndexOutOfBoundsException exception2(){
        throw new ArrayIndexOutOfBoundsException();
    }
}
