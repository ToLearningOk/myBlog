package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.User;
import com.djt.domain.vo.UserInfoVo;
import com.djt.mapper.UserMapper;
import com.djt.service.UserService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 18:25:04
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult getUserInfo() {
        User user = getById(SecurityUtils.getUserId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
//    用户信息更新
    public ResponseResult updateUserInfo(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,user.getId());
        updateWrapper.set(User::getAvatar,user.getAvatar());
        updateWrapper.set(User::getEmail,user.getEmail());
        updateWrapper.set(User::getId,user.getId());
        updateWrapper.set(User::getNickName, user.getNickName());
        updateWrapper.set(User::getSex, user.getSex());
        updateById(user);
        return ResponseResult.okResult();
    }
}
