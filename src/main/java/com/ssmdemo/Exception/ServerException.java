package com.ssmdemo.Exception;

import com.ssmdemo.common.CODE;

/**
 * 自定义exception
 * Created by zhuguangchuan on 2018/5/30.
 */
public class ServerException extends Exception {
    private CODE code;
    private String exceptionMsg;

    private ServerException(CODE code,String exceptionMsg){
        this.code = code ;
        this.exceptionMsg = exceptionMsg;
    }

    public CODE getCode() {
        return code;
    }

    public void setCode(CODE code) {
        this.code = code;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    /**
     * 构造ServerException
     * @param code
     * @param exceptionMsg
     * @return
     */
    public static ServerException build(CODE code,String exceptionMsg){
        return new ServerException(code, exceptionMsg);
    }
}
