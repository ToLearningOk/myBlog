package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.entity.Menu;
import com.djt.mapper.MenuMapper;
import com.djt.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.djt.constants.SystemConstants.*;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 15:21:23
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    MenuServiceImpl menuServiceImpl;

    /**
     * 根据id查询用户权限信息，返回字符串，如果是管理员返回所有权限
     * 否则返回所具有的权限
     * @param id 用户id
     * @return List<String>
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //是管理员
        if(id ==1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            //需要所有菜单类型为C或F的且状态为正常，未被删除的权限。  删除判断已有全局配置
            queryWrapper.in(Menu::getMenuType, MENU_TYPE_BUTTON,MENU_TYPE_MENU);
            queryWrapper.eq(Menu::getStatus,MENU_STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //不是管理员，返回对应的权限信息
        return getBaseMapper().getSelectPermsByID(id);


    }

}
