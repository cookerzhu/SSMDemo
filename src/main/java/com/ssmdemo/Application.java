package com.ssmdemo;

import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/25.
 * 如果是发布war包放在Servlet容器（e.g. Tomcat）里面，需要继承SpringBootServletInitializer，在Servlet容器启动
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements ApplicationRunner
 {
     private static final Logger log = Logger.getLogger(Application.class);

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


     @Override
     @Order(2)
     public void run(ApplicationArguments args) throws Exception {

         log.info("***********init something 2***********");
     }

 }

@Component
@Order(1)//low value, high priority
class bean1 implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("*********init something 1********");
    }
}
