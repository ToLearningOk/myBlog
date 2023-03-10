package com.djt.exception;

import com.djt.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException{
//统一异常处理的封装类
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
