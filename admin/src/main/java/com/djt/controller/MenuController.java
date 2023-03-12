package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Menu;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.service.MenuService;
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
        return ResponseResult.okResult(menuList);
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
     * 删除菜单
     * @param menuId
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
    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        List<Menu> menuList =  menuService.selectMenuList(menu);

    }



}
