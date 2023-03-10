package com.djt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.djt.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 15:21:22
 */
public interface MenuMapper extends BaseMapper<Menu> {
//    /***
//     * 根据id查询该用户的权限信息
//     * @param userId 用户id
//     * @return List<String>
//     */
    List<String> getSelectPermsByID(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouteTreeByUserId(Long userId);
}

