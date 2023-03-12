package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ChangeRoleStatusDto;
import com.djt.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 15:31:58
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询角色信息
     * @param id
     * @return List
     */
    List<String> selectRoleByUserId(Long id);

    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);

    void changeStatus(ChangeRoleStatusDto roleStatusDto);
}
