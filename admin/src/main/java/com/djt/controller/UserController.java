package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    UserService userService;
    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public ResponseResult listUser(User user, Integer pageNum, Integer pageSize){
        return userService.selectUserPage(user,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody  User user){
        //判断用户信息是否重复
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }
}
