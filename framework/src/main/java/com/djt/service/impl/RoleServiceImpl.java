package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.entity.Role;
import com.djt.domain.entity.User;
import com.djt.mapper.RoleMapper;
import com.djt.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 15:31:58
 */
@Service("sysRoleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {



    @Override
    public List<String> selectRoleByUserId(Long id) {
        //判断是否为管理员，是则返回集合中的只需要admin
        if(id==1L){
            List<String> roles = new ArrayList<>();
            roles.add("admin");
            return roles;
        }
        //不是管理员，查询用户具有的roles信息
        List<String> roles = getBaseMapper().SelectRolesById(id);

        return roles;
    }
}
