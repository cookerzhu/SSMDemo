package com.ssmdemo.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * print request parameters and response
 * Created by zhuguangchuan on 2018/5/21.
 */
@Aspect
@Component
@Log4j2
public class PrintRequestAspect {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(* com.ssmdemo.controller.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @AfterReturning(pointcut = "requestMapping()",returning = "returnVal")
    public void reqPrinter(JoinPoint joinPoint,Object returnVal){
        Map<String, Object> parameters = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> keys = parameterMap.keySet();
        for (Object key : keys) {
            String[] strArr = request.getParameterValues(key.toString());
            if (strArr.length > 0) {
                parameters.put(key.toString(), Arrays.toString(strArr));
            }
        }
        String point = joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName();
        log.info("{} -> request请求参数:{}",point,parameters.toString());
        log.info("{} -> 返回结果：{}" ,point,returnVal.toString());
    }

}
