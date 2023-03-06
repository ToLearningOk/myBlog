package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService service;

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(){
        return service.getUserInfo();
    }

}
