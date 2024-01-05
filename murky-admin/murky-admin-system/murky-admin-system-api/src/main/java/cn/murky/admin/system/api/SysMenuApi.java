package cn.murky.admin.system.api;

import cn.murky.admin.system.api.domian.SysMenuTree;
import cn.murky.admin.system.api.enums.MenuType;

import java.util.List;


/**
 * 菜单API
 */
public interface SysMenuApi {


    /**
     * 获取树形菜单
     *
     * @return 菜单树视图对象
     */
    List<SysMenuTree> treeSysMenu(List<MenuType> menuTypes);
 }