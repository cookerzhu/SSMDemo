package com.ssmdemo.aop;

import com.ssmdemo.annotation.ExampleAnnotation;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * example aspect
 * Created by zhuguangchuan on 2018/5/21.
 */
@Aspect
@Component
@Log4j2
public class ExampleAspect {

    @Around("@annotation(exampleAnnotation)") //@annotation注解里面的值必须和下面方法里的参数对应
    public Object check(ProceedingJoinPoint joinPoint, ExampleAnnotation exampleAnnotation) throws Throwable{
        String value = exampleAnnotation.value();
        String[] conditions = exampleAnnotation.conditions();
        log.info(value,conditions.toString());
        return joinPoint.proceed();
    }

    @Before(value = "execution(* com.ssmdemo.service.*.query(..)) && args(arg)",argNames="arg")
    private void queryBefore(String arg){
        System.out.println(arg);
    }
}
