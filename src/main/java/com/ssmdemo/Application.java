package com.ssmdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2016/11/25.
 * 如果是发布war包放在Servlet容器（e.g. Tomcat）里面，需要继承SpringBootServletInitializer，在Servlet容器启动
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }
 }

