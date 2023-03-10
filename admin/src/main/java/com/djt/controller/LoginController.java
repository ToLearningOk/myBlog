package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.LoginUser;
import com.djt.domain.entity.Menu;
import com.djt.domain.entity.User;
import com.djt.domain.vo.AdminUserInfoVo;
import com.djt.domain.vo.RoutsVo;
import com.djt.domain.vo.UserInfoVo;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.service.LoginService;
import com.djt.service.MenuService;
import com.djt.service.RoleService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class LoginController {
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;
    @Resource
    private LoginService LoginService;
    @PostMapping("/user/login")
    public ResponseResult Login(@RequestBody User user){
        if(!Strings.hasText(user.getUserName()))
            //提示需要用户名
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        return LoginService.login(user);
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //1.获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //2.根据当前用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //3.查询用户角色信息
        List<String> rolesKeyList = roleService.selectRoleByUserId(loginUser.getUser().getId());
        //4.获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装返回
        AdminUserInfoVo adminUserInfo = new AdminUserInfoVo(perms,rolesKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfo);
    }
    @GetMapping("/getRouters")
    public ResponseResult<RoutsVo> getRouters(){

        //获取用户信息
        Long userId = SecurityUtils.getUserId();
        //查询menu,结果是tree的形式
        List<Menu> menus = menuService.selectMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutsVo(menus));
    }
}
