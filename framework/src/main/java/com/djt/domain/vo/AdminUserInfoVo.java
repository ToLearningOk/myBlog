package com.djt.domain.vo;


import com.djt.domain.entity.Role;
import com.djt.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AdminUserInfoVo {
    private UserInfoVo user;
    private List<String> permissions;
    private List<String> roles;


    public AdminUserInfoVo(List<String> perms, List<String> roles, UserInfoVo userInfoVo) {
        this.user = userInfoVo;
        this.permissions = perms;
        this.roles = roles;
    }
}
