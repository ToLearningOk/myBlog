package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import io.jsonwebtoken.lang.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
public class LoginController {
    @Resource
    com.djt.service.LoginService LoginService;
    @PostMapping("/user/login")
    public ResponseResult Login(@RequestBody User user){
        if(!Strings.hasText(user.getUserName()))
            //提示需要用户名
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        return LoginService.login(user);
    }
}
