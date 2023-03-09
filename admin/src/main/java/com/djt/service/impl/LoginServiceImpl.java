package com.djt.service.impl;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.LoginUser;
import com.djt.domain.entity.User;
import com.djt.domain.vo.BlogUserLoginVo;
import com.djt.domain.vo.UserInfoVo;
import com.djt.service.BlogLoginService;
import com.djt.service.LoginService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.JwtUtil;
import com.djt.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource

    private RedisCache redisCache;

    /**后台登录登录
     * */
    @Override
    public ResponseResult login(User user) {
        //通过前端封装的user对象，获取name和password生成 token验证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        // 用创建的bean身份管理器，使用重写的  loadUserByUsername    验证token内信息
        // 返回验证后的完整用户信息包装(使用token来解码封装用户信息，并设置权限)
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if(Objects.isNull(authenticate))
            throw new RuntimeException("用户名或密码错误");
        //获取 user 生成 jwt
        // 1.获取User
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

        String userId = loginUser.getUser().getId().toString();
        //2.获取userid，生成 JWT token。--LoginUser实现了UserDetail
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId, loginUser);

        //封装token并返回
        Map<String,String> map= new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取封装成authentication的Token，解析userID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userId
        String userId = loginUser.getUser().getId().toString();

        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }

}
