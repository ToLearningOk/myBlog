package com.djt.service.impl;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.LoginUser;
import com.djt.domain.entity.User;
import com.djt.domain.vo.BlogUserLoginVo;
import com.djt.domain.vo.UserInfoVo;
import com.djt.service.BlogLoginService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.JwtUtil;
import com.djt.utils.RedisCache;
import io.jsonwebtoken.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource

    private RedisCache redisCache;

    /**博客登录
     * */
    @Override
    public ResponseResult login(User user) {
        //通过前端封装的user对象，获取name和password生成 token验证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        // 用创建的bean身份管理器，使用重写的  loadUserByUsername    验证token内信息
        // 返回验证后的完整用户信息包装(使用token来解码封装用户信息，并设置权限)
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//  ——————————修改结束，容器继续执行，—————下面为个人操作———————————

        //判断是否认证通过
        if(Objects.isNull(authenticate)) throw new RuntimeException("用户名或密码错误");

        // 1.获取User，强行转换成LoginUser 2.获取userid，生成 JWT token。--LoginUser实现了UserDetail
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId, loginUser);

        //把token和userinfo封装 返回
        //把User转换成 UserInfo  loginUser.getUser->userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo Vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(Vo);
    }

    @Override
    public ResponseResult logout() {
        //获取封装成authentication的Token，解析userID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userId
        String userId = loginUser.getUser().getId().toString();

        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }

}
