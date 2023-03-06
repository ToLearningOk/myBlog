package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;
//  用户登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user, String password){
        if(!StringUtils.hasText(user.getUserName())){
            //提示必须要传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
