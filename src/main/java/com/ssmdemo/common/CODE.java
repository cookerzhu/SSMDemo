package com.ssmdemo.common;

/**
 * 返回状态码和消息
 * Created by zhuguangchuan on 2018/5/30.
 */
public enum CODE {
    Success("S000","成功"),
    Fail("F001","系统异常");
    private String code;
    private String msg;

    CODE(String code,String msg){
        this.code = code;
        this.msg = msg ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{" +
                "code:'" + code + '\'' +
                ", msg:'" + msg + '\'' +
                '}';
    }
}
