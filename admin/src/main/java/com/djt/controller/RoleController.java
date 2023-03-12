package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ChangeRoleStatusDto;
import com.djt.domain.entity.Role;
import com.djt.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Resource
    RoleService roleService;
    @GetMapping("/list")
    public ResponseResult listAllRole(Role role, Integer pageNum, Integer pageSize){

        return roleService.selectRolePage(role,pageNum,pageSize);
    }
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        roleService.changeStatus(roleStatusDto);
        return ResponseResult.okResult();
    }
}
