package cn.murky.admin.tenant.mapper;

import cn.murky.admin.tenant.domain.dto.TenantPageDTO;
import cn.murky.admin.tenant.domain.entity.Tenant;
import cn.murky.admin.tenant.domain.entity.table.TenantPermissionGroupTableDef;
import cn.murky.admin.tenant.domain.entity.table.TenantTableDef;
import cn.murky.admin.tenant.domain.vo.TenantVo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;

import java.time.LocalDateTime;

/**
 * TenantMapper
 *
 * @Author hans
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 分页sql
     * @param tenantPageDTO 分页条件
     */
    default Page<TenantVo> page(TenantPageDTO tenantPageDTO) {
        TenantTableDef TENANT=TenantTableDef.TENANT;
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        QueryWrapper queryWrapper = QueryWrapper.create().from(TENANT)
                .innerJoin(TENANT_PERMISSION_GROUP).on(TENANT.GROUP_ID.eq(TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(TENANT.TENANT_NAME.like(tenantPageDTO.getTenantName(), If::hasText))
                .orderBy(TENANT.UPDATE_TIME.desc());

        return paginateAs(tenantPageDTO.getPageNumber(), tenantPageDTO.getPageSize(),queryWrapper, TenantVo.class);
    }

    /**
     * 租户详情sql
     * @param tenantId 租户id
     */
    default TenantVo info(Long tenantId) {
        TenantTableDef TENANT=TenantTableDef.TENANT;
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        QueryWrapper queryWrapper = QueryWrapper.create().from(TENANT)
                .innerJoin(TENANT_PERMISSION_GROUP).on(TENANT.GROUP_ID.eq(TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(TENANT.ID.eq(tenantId));

        return selectOneByQueryAs(queryWrapper, TenantVo.class);
    }

    /**
     * 租户名是否存在
     * @param tenantName 租户名
     */
    default long selectCountByTenantName(String tenantName) {
        TenantTableDef TENANT=TenantTableDef.TENANT;
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        QueryWrapper queryWrapper = QueryWrapper.create().from(TENANT)
                .innerJoin(TENANT_PERMISSION_GROUP).on(TENANT.GROUP_ID.eq(TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(TENANT.TENANT_NAME.eq(tenantName));

        return selectCountByQuery(queryWrapper);
    }

    /**
     * 根据给定的参数更新租户的名称、过期时间和分组ID，并按租户ID进行更新操作。
     *
     * @param tenantId 租户ID
     * @param tenantName 租户名称
     * @param expires 过期时间
     * @param groupId 分组ID
     * @return 更新的租户数量
     */
    default int updateNameAndExpiresAndGroupByTenantId(Long tenantId, String tenantName, LocalDateTime expires, Long groupId) {
        Tenant tenant = UpdateEntity.of(Tenant.class, tenantId)
                .setTenantName(tenantName)
                .setExpires(expires)
                .setGroupId(groupId);
        return update(tenant);
    }
}
