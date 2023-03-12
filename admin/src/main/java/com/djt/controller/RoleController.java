package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ChangeRoleStatusDto;
import com.djt.domain.entity.Role;
import com.djt.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody  Role role){
        roleService.save(role);
        return ResponseResult.okResult();
    }

    /**
     * 根据角色编号id获取详细信息
     * @param roleId
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getRoleInfo(@PathVariable("id") Long roleId){
        return ResponseResult.okResult(roleService.getById(roleId));
    }

    /**
     * 根据返回的角色信息，来更新角色信息接口
     * @param role
     * @return
     */
    @PutMapping
    public ResponseResult editRole(@RequestBody Role role){
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }
    //删除角色
    @DeleteMapping("{id}")
    public ResponseResult removeRole(@PathVariable(name = "id") Long id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
    /**
     * 查询角色列表
     */
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        List<Role> roles = roleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }


}
