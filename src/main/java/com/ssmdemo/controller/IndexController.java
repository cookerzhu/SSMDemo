package com.ssmdemo.controller;

import com.ssmdemo.annotation.ExampleAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping
    @ResponseBody
    public Object printParameter(@RequestParam("userId")String userId){
        return null ;
    }
}
