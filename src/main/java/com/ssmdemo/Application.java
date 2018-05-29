package com.ssmdemo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2016/11/25.
 * 如果是发布war包放在Servlet容器（e.g. Tomcat）里面，需要继承SpringBootServletInitializer，在Servlet容器启动
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }
 }

