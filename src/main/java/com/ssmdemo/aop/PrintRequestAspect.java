package com.ssmdemo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * print request parameters
 * Created by zhuguangchuan on 2018/5/21.
 */
@Aspect
@Component
public class PrintRequestAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Around("requestMapping()")
    public Object reqPrinter(final ProceedingJoinPoint joinPoint) throws Throwable{
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap.toString());

        return joinPoint.proceed();
    }

}
