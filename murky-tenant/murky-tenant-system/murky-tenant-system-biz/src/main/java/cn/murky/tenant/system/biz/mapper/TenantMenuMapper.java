package cn.murky.tenant.system.biz.mapper;

import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.system.biz.domian.entity.TenantMenu;
import cn.murky.tenant.system.biz.domian.entity.table.SysRoleMenuTableDef;
import cn.murky.tenant.system.biz.domian.entity.table.TenantMenuTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.exists;

public interface TenantMenuMapper extends BaseMapper<TenantMenu> {
    /**
     * 根据角色id和菜单类型查询对应权限
     * @param menuTypes 菜单类型
     * @param fkRoleId 角色id
     * @return
     */
    default List<TenantMenu> selectByMenuTypeAndfkRoleid(List<MenuType> menuTypes, Long fkRoleId) {

        SysRoleMenuTableDef SYS_ROLE_MENU = SysRoleMenuTableDef.SYS_ROLE_MENU;
        TenantMenuTableDef TENANT_MENU = TenantMenuTableDef.TENANT_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(TENANT_MENU)
                        .where(TENANT_MENU.TYPE.in(menuTypes))
                        //租户管理员查询全部数据
                        .and(
                                exists
                                        (
                                                QueryWrapper.create().select(SYS_ROLE_MENU.FK_MENU_ID)
                                                        .from(SYS_ROLE_MENU)
                                                        .where(SYS_ROLE_MENU.FK_ROLE_ID.eq(fkRoleId))
                                                        .and(TENANT_MENU.ID.eq(SYS_ROLE_MENU.FK_MENU_ID))
                                        )
                        )
                        .orderBy(TENANT_MENU.SORT.asc(), TENANT_MENU.LABEL.asc())
        );
    }

    /**
     * 根据权限码查询菜单
     * @param auths 权限码
     */
    default List<TenantMenu> selectListByAuths(List<String> auths) {

        TenantMenuTableDef TENANT_MENU = TenantMenuTableDef.TENANT_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(TENANT_MENU)
                        .where(TENANT_MENU.AUTH.in(auths))
                        .orderBy(TENANT_MENU.SORT.asc(), TENANT_MENU.LABEL.asc())
        );
    }
}
