package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.djt.constants.SystemConstants;
import com.djt.domain.entity.LoginUser;
import com.djt.domain.entity.User;
import com.djt.mapper.MenuMapper;
import com.djt.mapper.UserMapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 重写的detail查询
 * */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息，
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);
        //判断是否查询用户，没有抛出异常
        if(Objects.isNull(user)) throw new RuntimeException("用户不存在");
        //封装用户信息并返回
        //如果是后台用户才需要查询权限封装
        if(user .getType().equals(SystemConstants.ADMIN)) {
            List<String> permsByID = menuMapper.getSelectPermsByID(user.getId());
            return new LoginUser(user,permsByID);
        }
        return new LoginUser(user,null);
    }
}
