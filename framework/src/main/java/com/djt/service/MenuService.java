package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 15:21:23
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
}
