package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;
import com.djt.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){//参数在请求体中，用RequestBody标识，用实体类来接受参数
        return service.updateUserInfo(user);
    }

}
