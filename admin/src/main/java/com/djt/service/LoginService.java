package com.djt.service;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);
    ResponseResult logout();

}
