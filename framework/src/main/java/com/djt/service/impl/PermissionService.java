package com.djt.service.impl;

import com.djt.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
@Slf4j
public class PermissionService {
    /**
     * 判断当前用户是否居然permission
     * @param permission 需要判断的权限
     * @return
     */
    public Boolean hasPermission(String permission){
        //如果是超级管理员 直接返回true，
        if (SecurityUtils.isAdmin()){
            log.info("这是管理员");
            return true;
        }
        // 否则获取当前用户所具有的权限类比是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermission();
        log.info("这是非管理员用户");
        return permissions.contains(permission);

    }
}
