package com.ssmdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuguangchuan on 2018/5/2.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/home")
    public String home(){
        return "index";
    }
}
