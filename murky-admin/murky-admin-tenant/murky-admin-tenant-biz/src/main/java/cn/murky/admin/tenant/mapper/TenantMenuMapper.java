package cn.murky.admin.tenant.mapper;


import cn.murky.admin.tenant.domain.entity.TenantMenu;
import cn.murky.admin.tenant.domain.entity.table.TenantGroupMenuTableDef;
import cn.murky.admin.tenant.domain.entity.table.TenantMenuTableDef;
import cn.murky.admin.tenant.enums.TenantMenuType;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

/**
 * 商户菜单Mapper
 *
 * @Author hans
 */
public interface TenantMenuMapper extends BaseMapper<TenantMenu> {

    /**
     * 根据menuTypes查询
     *
     * @param menuTypes 菜单类型
     * @return TenantMenu
     */
    default List<TenantMenu> selectByMenuType(List<TenantMenuType> menuTypes) {
        TenantMenuTableDef TENANT_MENU = TenantMenuTableDef.TENANT_MENU;
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select().from(TENANT_MENU)
                .where(TENANT_MENU.TYPE.in(menuTypes))
                .orderBy(TENANT_MENU.SORT.asc(), TENANT_MENU.LABEL.asc());

        return this.selectListByQuery(queryWrapper);
    }

    /**
     * 通过菜单ID列表查询菜单列表
     *
     * @param tenantMenuIds 菜单ID列表
     * @return 菜单列表
     */
    default List<TenantMenu> selectByListByIds(List<Long> tenantMenuIds) {

        TenantMenuTableDef TENANT_MENU = TenantMenuTableDef.TENANT_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(TENANT_MENU)
                        .where(TENANT_MENU.ID.in(tenantMenuIds))
                        .orderBy(TENANT_MENU.SORT.asc(), TENANT_MENU.LABEL.asc())
        );
    }

    /**
     * 根据groupId查询商户菜单
     * @param groupId 商户角色id
     * @return 商户菜单实体对象
     */
    default List<TenantMenu> selectByGroupId(Long groupId) {
        TenantGroupMenuTableDef TENANT_GROUP_MENU = TenantGroupMenuTableDef.TENANT_GROUP_MENU;
        TenantMenuTableDef TENANT_MENU = TenantMenuTableDef.TENANT_MENU;
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(TENANT_MENU).leftJoin(TENANT_GROUP_MENU)
                        .on(TENANT_GROUP_MENU.FK_MENU_ID.eq(TENANT_MENU.ID))
                        .where(TENANT_GROUP_MENU.FK_GROUP_ID.eq(groupId))
                        .orderBy(TENANT_MENU.SORT.asc(), TENANT_MENU.LABEL.asc())
        );
    }
}
