package com.djt.handler.exception;

import com.djt.domain.ResponseResult;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
/**统一异常处理*/
public class GlobalExceptionHandler {
    //调用systemException异常时 获取并处理
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }
    //处理其他异常
    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),
                                          AppHttpCodeEnum.SYSTEM_ERROR.getMsg());

    }
}
