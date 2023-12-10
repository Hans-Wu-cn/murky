package cn.poem.solon.admin.system.api;

import cn.poem.solon.admin.system.api.domian.PoemMenuTree;
import cn.poem.solon.admin.system.api.enums.MenuType;

import java.util.List;


/**
 * 菜单API
 */
public interface PoemMenuApi {


    /**
     * 获取树形菜单
     *
     * @return 菜单树视图对象
     */
    List<PoemMenuTree> treePoemMenu(List<MenuType> menuTypes);
 }