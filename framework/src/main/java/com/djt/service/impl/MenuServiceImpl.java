package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.entity.Article;
import com.djt.domain.entity.Menu;
import com.djt.mapper.MenuMapper;
import com.djt.service.MenuService;
import com.djt.utils.SecurityUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
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
        //此用户是管理员，返回所有权限
        if(SecurityUtils.isAdmin()){
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

    /**
     * 查询该用户的 Menu 数据，以树结构返回
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus=null;
        //判断是否是管理员 (userId为1L)
        if(SecurityUtils.isAdmin()){
            //如果是，返回所有符合的Menu （菜单类型是C或M，状态正常，未被删除的权限）
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //否则，返回当前用户所具有的Menu
            menus = menuMapper.selectRouteTreeByUserId(userId);
        }

        //构建tree
        //先找第一层级的菜单，然后去找它们的子菜单设置到children中
        List<Menu> menuTree = buildMenuTree(menus,0L);

        return menuTree;
    }

    /**
     * 查询所有的可使用菜单
     * @param menu
     * @return
     */
    @Override
    public List<Menu> selectMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询并排除已删除的菜单
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序,根据父菜单id和orderNum
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        return menus;

    }

    /**
     * 判断当前菜单是否存在子菜单
     * @param menuId
     * @return
     */
    @Override
    public boolean hasChild(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return !Objects.isNull(getOne(queryWrapper));
    }

    /**
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }


//    ——————————————————————————分割线，下方为实现内部功能调用的方法
    /**
     * 将传入的menus参数构建成tree结构
     * @param menus
     * @param prentId
     * @return
     */
    private List<Menu> buildMenuTree(List<Menu> menus, long prentId) {
        //只能查询二层级数据
//        for (Menu menu : menus) {
//            Long id = menu.getId();
//            for (Menu menu1 : menus) {
//                if(menu1.getParentId().equals(id))
//                    menu.getChildren().add(menu1);
//            }
//        }

        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(prentId))
                .map(menu -> menu.setChildren(getChildrenMenu(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;

        //根据第一层就

    }

    /**
     * 获取传入参数 menu 的所有的 子menu 集合
     * @param menu 传入的当前级 menu（菜单）
     * @param menus 包含当前级 menu 和所有 子menu (菜单)的集合
     * @return List
     */
    private List<Menu> getChildrenMenu(Menu menu, List<Menu> menus) {
//        优化，该方案是查询数据库获取所有 菜单menu 集合，更新后，menu集合直接由外部传入
//        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.eq(Menu::getParentId,menu.getId());
//        List<Menu> menuList = getBaseMapper().selectList(queryWrapper);
        List<Menu> menuChildList = menus.stream()
                //过滤 menu 集合中不是子menu 的部分
                .filter(childMenu -> childMenu.getParentId().equals(menu.getId()))
                //调用set，给次级menu添加子菜单 构造 1 -> 2-> 3 -> 1 的递归循环
                .map(m-> m.setChildren(getChildrenMenu(m, menus)))
                .collect(Collectors.toList());
        return menuChildList;
    }
}
