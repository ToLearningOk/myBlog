package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Role;
import com.djt.domain.entity.User;
import com.djt.domain.vo.UserInfoAndRoleIdsVo;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.service.RoleService;
import com.djt.service.UserService;
import com.djt.utils.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

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

    /**
     * 删除用户
     * @param id
     */
    @DeleteMapping("{id}")
    public ResponseResult remove(@PathVariable("id") List<Long> id){
        if(id.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前正在使用的用户");
        }
        userService.removeByIds(id);
        return ResponseResult.okResult();
    }

    /**
     * 查询用户回显接口
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getUserInfoAndRoleIds(@PathVariable("id") Long id){
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(id);
        //当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(id);

        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return ResponseResult.okResult(vo);
    }
    //保存更新的用户信息
    @PutMapping
    public ResponseResult edit (@RequestBody User user){
        userService.save(user);
        return ResponseResult.okResult();
    }
}
