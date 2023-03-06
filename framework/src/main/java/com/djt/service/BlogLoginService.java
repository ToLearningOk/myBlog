package com.djt.service;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
