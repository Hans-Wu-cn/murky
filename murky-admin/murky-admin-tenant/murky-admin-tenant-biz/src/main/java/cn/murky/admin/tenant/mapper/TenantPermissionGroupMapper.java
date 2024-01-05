package cn.murky.admin.tenant.mapper;

import cn.murky.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.murky.admin.tenant.domain.entity.table.TenantPermissionGroupTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * 商户权限组mapper
 */
public interface TenantPermissionGroupMapper extends BaseMapper<TenantPermissionGroup> {


    /**
     * 根据商户商户权限组名查询数量
     *
     * @return 返回数量
     */
    default TenantPermissionGroup selectByRoleNameAndRoleCode(String name) {
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        return this.selectOneByQuery(QueryWrapper.create()
                .from(TENANT_PERMISSION_GROUP).where(TENANT_PERMISSION_GROUP.GROUP_NAME.eq(name)).limit(1)
        );
    }

    /**
     * 根据权限组名称查询权限组信息
     *
     * @param groupId 权限组id
     * @param groupName   权限组名称
     * @return 符合条件的权限组集合
     */
    default TenantPermissionGroup selectByNameOrCode(Long groupId, String groupName) {
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        return this.selectOneByQuery(QueryWrapper.create().where(
                TENANT_PERMISSION_GROUP.GROUP_ID.ne(groupId, If::notNull)
        ).or(TENANT_PERMISSION_GROUP.GROUP_NAME.eq(groupName)).limit(1));
    }


}
