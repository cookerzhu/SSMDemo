package com.ssmdemo.common;

/**
 * 返回数据
 * Created by zhuguangchuan on 2018/5/30.
 */
public class ResponseData {
    private Object data;
    private String code;
    private String msg;

    public ResponseData(CODE code){
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public ResponseData(CODE code ,Object data) {
        this(code);
        this.data = data ;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

}
