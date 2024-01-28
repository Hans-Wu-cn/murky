package cn.murky.tenant.system.biz.mapper;

import cn.murky.tenant.system.biz.domian.entity.TenantGroupMenu;
import cn.murky.tenant.system.biz.domian.entity.table.TenantGroupMenuTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface TenantGroupMenuMapper extends BaseMapper<TenantGroupMenu> {


    /**
     * 根据权限组id查询权限
     * @param fkGroupId 权限组id
     */
    default List<TenantGroupMenu> selectListByFkGroupId(Long fkGroupId) {
        TenantGroupMenuTableDef TENANT_GROUP_MENU = TenantGroupMenuTableDef.TENANT_GROUP_MENU;
        return this.selectListByQuery(QueryWrapper.create().where(TENANT_GROUP_MENU.FK_GROUP_ID.eq(fkGroupId)));
    }
}
