package com.djt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.entity.UserRole;
import com.djt.mapper.UserRoleMapper;
import com.djt.service.UserRoleService;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
