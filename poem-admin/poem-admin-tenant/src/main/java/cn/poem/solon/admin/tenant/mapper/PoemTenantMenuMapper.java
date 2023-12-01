package cn.poem.solon.admin.tenant.mapper;


import cn.poem.solon.admin.tenant.domain.entity.PoemTenantMenu;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantGroupMenuTableDef;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantMenuTableDef;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantPermissionGroupTableDef;
import cn.poem.solon.admin.security.enums.MenuType;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.List;

/**
 * 商户菜单Mapper
 *
 * @Author hans
 */
public interface PoemTenantMenuMapper extends BaseMapper<PoemTenantMenu> {
    PoemTenantMenuTableDef POEM_TENANT_MENU = PoemTenantMenuTableDef.POEM_TENANT_MENU;
    PoemTenantPermissionGroupTableDef POEM_TENANT_PERMISSION_GROUP = PoemTenantPermissionGroupTableDef.POEM_TENANT_PERMISSION_GROUP;
    PoemTenantGroupMenuTableDef POEM_TENANT_GROUP_MENU = PoemTenantGroupMenuTableDef.POEM_TENANT_GROUP_MENU;

    /**
     * 根据menuTypes查询
     *
     * @param menuTypes 菜单类型
     * @return PoemTenantMenu
     */
    default List<PoemTenantMenu> selectByMenuType(List<MenuType> menuTypes) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select().from(POEM_TENANT_MENU)
                .where(POEM_TENANT_MENU.TYPE.in(menuTypes))
                .orderBy(POEM_TENANT_MENU.SORT.asc(), POEM_TENANT_MENU.LABEL.asc());

        return this.selectListByQuery(queryWrapper);
    }

    /**
     * 通过菜单ID列表查询菜单列表
     *
     * @param tenantMenuIds 菜单ID列表
     * @return 菜单列表
     */
    default List<PoemTenantMenu> selectByListByIds(List<Long> tenantMenuIds) {

        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(POEM_TENANT_MENU)
                        .where(POEM_TENANT_MENU.TENANT_MENU_ID.in(tenantMenuIds))
                        .orderBy(POEM_TENANT_MENU.SORT.asc(), POEM_TENANT_MENU.LABEL.asc())
        );
    }

    /**
     * 根据groupId查询商户菜单
     * @param groupId 商户角色id
     * @return 商户菜单实体对象
     */
    default List<PoemTenantMenu> selectByGroupId(Long groupId) {

        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(POEM_TENANT_MENU).leftJoin(POEM_TENANT_PERMISSION_GROUP)
                        .on(POEM_TENANT_GROUP_MENU.TENANT_MENU_ID.eq(POEM_TENANT_MENU.TENANT_MENU_ID))
                        .where(POEM_TENANT_PERMISSION_GROUP.GROUP_ID.eq(groupId))
                        .orderBy(POEM_TENANT_MENU.SORT.asc(), POEM_TENANT_MENU.LABEL.asc())
        );
    }
}
