package com.djt.domain.vo;

import com.djt.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutsVo {
    //子菜单
    private List<Menu> menus;

//    //组件路径
//    private String component;
//    //创建时间
//    private Date createTime;
//    //菜单图标
//    private String icon;
//    //菜单ID@TableId
//    private Long id;
//    //菜单名称
//    private String menuName;
//    //菜单类型（M目录 C菜单 F按钮）
//    private String menuType;
//    //显示顺序
//    private Integer orderNum;
//    //父菜单ID
//    private Long parentId;
//    //路由地址
//    private String path;
//    //权限标识
//    private String perms;
//    //菜单状态（0正常 1停用）
//    private String status;
//    //菜单状态（0显示 1隐藏）
//    private String visible;

}
