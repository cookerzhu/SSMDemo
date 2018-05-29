package com.ssmdemo.controller;

import com.ssmdemo.annotation.ExampleAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuguangchuan on 2018/5/2.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/home")
    @ExampleAnnotation(value = "test",conditions = {"A","B"})
    public String home(){
        return "index";
    }

}
