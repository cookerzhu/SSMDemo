package com.ssmdemo.Exception;

import com.ssmdemo.common.CODE;
import com.ssmdemo.common.ResponseData;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * global exception handler
 * Created by zhugc on 2018/4/25-21:39.
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseData handle(Exception e){
        e.printStackTrace();
        String msg ;
        CODE code ;
        if(e instanceof ServerException){
            msg = ((ServerException) e).getExceptionMsg();
            code = ((ServerException) e).getCode();
        }else{
            msg = e.getMessage();
            code = CODE.Fail;
        }
        log.error("catch exception : " + msg);
        return new ResponseData(code);
    }
}
