package cn.poem.solon.admin.tenant.mapper;

import cn.poem.solon.admin.tenant.domain.entity.PoemTenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantPermissionGroupTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * 商户权限组mapper
 */
public interface PoemTenantPermissionGroupMapper extends BaseMapper<PoemTenantPermissionGroup> {
    PoemTenantPermissionGroupTableDef POEM_TENANT_PERMISSION_GROUP = PoemTenantPermissionGroupTableDef.POEM_TENANT_PERMISSION_GROUP;


    /**
     * 根据商户商户权限组名查询数量
     *
     * @return 返回数量
     */
    default PoemTenantPermissionGroup selectByRoleNameAndRoleCode(String name) {
        return this.selectOneByQuery(QueryWrapper.create()
                .from(POEM_TENANT_PERMISSION_GROUP).where(POEM_TENANT_PERMISSION_GROUP.GROUP_NAME.eq(name)).limit(1)
        );
    }

    /**
     * 根据权限组名称查询权限组信息
     *
     * @param groupId 权限组id
     * @param groupName   权限组名称
     * @return 符合条件的权限组集合
     */
    default PoemTenantPermissionGroup selectByNameOrCode(Long groupId, String groupName) {
        return this.selectOneByQuery(QueryWrapper.create().where(
                POEM_TENANT_PERMISSION_GROUP.GROUP_ID.ne(groupId, If::notNull)
        ).or(POEM_TENANT_PERMISSION_GROUP.GROUP_NAME.eq(groupName)).limit(1));
    }


}
