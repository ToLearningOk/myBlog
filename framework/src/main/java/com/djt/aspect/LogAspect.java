package com.djt.aspect;

import com.alibaba.fastjson.JSON;
import com.djt.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**切面类
 * */
@Component
@Aspect
@Slf4j
public class LogAspect {
    //标识了SystemLog的方法都回打印日志
    @Pointcut("@annotation(com.djt.annotation.SystemLog)")
    public void pt(){
    }


    //通知方法
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
                                            //joinPoint相当于被增强的方法的信息
        Object proceed ;
        try {
            handleBefore(joinPoint);
            proceed = joinPoint.proceed();//proceed() 方法的调用相当于目标方法的调用，peoceed相当于目标方法执行后的返回值
            handleAfter();
        }finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator()/**拼接的系统换行符*/);
        }
        return proceed;//作为切面的返回值
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //TODO 正常该转为ServletRequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.BusinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringType().getTypeName(),
                                            ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSON( joinPoint.getArgs()) );
    }
    private void handleAfter() {
        // 打印出参
        log.info("Response       : {}", "");
    }
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        //((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SystemLog.class)
        return systemLog;
    }
}
