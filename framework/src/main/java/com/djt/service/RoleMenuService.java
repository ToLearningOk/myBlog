package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.entity.RoleMenu;


public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}