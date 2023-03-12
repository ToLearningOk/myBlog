package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Menu;
import com.djt.domain.vo.MenuTreeVo;
import com.djt.domain.vo.MenuVo;
import com.djt.domain.vo.RoleMenuTreeSelectVo;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.service.MenuService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.SystemConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Slf4j
public class MenuController {
    @Resource
    MenuService menuService;
    /**
     * 查询菜单列表
     */
    @GetMapping("/list")
    public ResponseResult MenuList(Menu menu){
        List<Menu> menuList =  menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }
    /**
     * 添加菜单
     */
    @PostMapping()
    public ResponseResult addMenu(@RequestBody Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询菜单数据
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult updateMenu(@PathVariable Long id){
        return ResponseResult.okResult(menuService.getById(id));
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PutMapping()
    public ResponseResult edit(@RequestBody Menu menu){
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult
                    .errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }
    /**
     * 根据菜单id删除菜单
     * @param menuId 菜单id
     * @return
     */
    @DeleteMapping("{menuId}")
    public ResponseResult remove(@PathVariable("menuId") Long menuId){
        //判断当前菜单是否存在子菜单，存在返回不可修改
        if (menuService.hasChild(menuId)){
            log.info("判断有子菜单");
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_NOT_REMOVE);
        }
        //不存在子菜单直接删除
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        //调用SystemConverter的方法，返回树类型结构
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult treeSelect(@PathVariable("id") Long roleId){
        List<Menu> menus = menuService.selectMenuList(new Menu());
        //查询当前角色的可用菜单
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        //将菜单转化为树结构
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        //Vo转换
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }




}
