package cn.poem.solon.admin.system.mapper;


import cn.poem.solon.admin.system.api.enums.MenuType;
import cn.poem.solon.admin.system.domain.entity.SysMenu;
import cn.poem.solon.admin.system.domain.entity.table.SysMenuTableDef;
import cn.poem.solon.admin.system.domain.entity.table.SysRoleMenuTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.Utils;

import java.util.Collection;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.exists;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    default List<SysMenu> selectByMenuType(List<MenuType> menuTypes, Collection<Long> roleids) {

        SysRoleMenuTableDef SYS_ROLE_MENU = SysRoleMenuTableDef.SYS_ROLE_MENU;
        SysMenuTableDef SYS_MENU = SysMenuTableDef.SYS_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(SYS_MENU)
                        .where(SYS_MENU.TYPE.in(menuTypes))
                        //超级管理员查询全部数据
                        .and(
                                exists
                                        (
                                                QueryWrapper.create().select(SYS_ROLE_MENU.MENU_ID)
                                                        .from(SYS_ROLE_MENU)
                                                        .where(SYS_ROLE_MENU.ROLE_ID.in(roleids))
                                                        .and(SYS_MENU.MENU_ID.eq(SYS_ROLE_MENU.MENU_ID))
                                        ).when(Utils.isNotEmpty(roleids))
                        )
                        .orderBy(SYS_MENU.SORT.asc(), SYS_MENU.LABEL.asc())
        );
    }

    default List<SysMenu> selectByListByIds(List<Long> menuIds) {

        SysMenuTableDef SYS_MENU = SysMenuTableDef.SYS_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(SYS_MENU)
                        .where(SYS_MENU.MENU_ID.in(menuIds))
                        .orderBy(SYS_MENU.SORT.asc(), SYS_MENU.LABEL.asc())
        );
    }

    /**
     * 根据roleId查询菜单
     * @param roleId 角色id
     * @return 菜单实体对象
     */
    default List<SysMenu> selectByRoleId(Long roleId) {

        SysRoleMenuTableDef SYS_ROLE_MENU = SysRoleMenuTableDef.SYS_ROLE_MENU;
        SysMenuTableDef SYS_MENU = SysMenuTableDef.SYS_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(SYS_MENU).leftJoin(SYS_ROLE_MENU)
                        .on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.MENU_ID))
                        .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId))
                        .orderBy(SYS_MENU.SORT.asc(), SYS_MENU.LABEL.asc())
        );
    }
}
