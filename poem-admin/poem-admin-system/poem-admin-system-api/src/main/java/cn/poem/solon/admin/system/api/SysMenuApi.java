package cn.poem.solon.admin.system.api;

import cn.poem.solon.admin.system.api.domian.SysMenuTree;
import cn.poem.solon.admin.system.api.enums.MenuType;

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
    List<SysMenuTree> treePoemMenu(List<MenuType> menuTypes);
 }