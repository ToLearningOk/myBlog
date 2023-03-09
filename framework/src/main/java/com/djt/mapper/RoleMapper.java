package com.djt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.djt.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 15:31:57
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> SelectRolesById(Long userId);
}

