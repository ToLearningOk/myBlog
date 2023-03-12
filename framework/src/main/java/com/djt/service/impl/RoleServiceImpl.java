package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ChangeRoleStatusDto;
import com.djt.domain.entity.Role;
import com.djt.domain.vo.PageVo;
import com.djt.mapper.RoleMapper;
import com.djt.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 分页查询角色名称，要求模糊查询，针对状态查询，和按照role_sort升序
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //目前没有根据id查询
        lambdaQueryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
        lambdaQueryWrapper.eq(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());
        lambdaQueryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,lambdaQueryWrapper);

        //转换成VO
        List<Role> roles = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(roles);
        return ResponseResult.okResult(pageVo);

    }

    /**
     * 改变角色状态
     *
     * @param roleStatusDto
     */
    @Override
    public void changeStatus(ChangeRoleStatusDto roleStatusDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(Role::getId,roleStatusDto.getRoleId());
        updateWrapper.set(Role::getStatus,roleStatusDto.getStatus());
        update(updateWrapper);
    }
}
