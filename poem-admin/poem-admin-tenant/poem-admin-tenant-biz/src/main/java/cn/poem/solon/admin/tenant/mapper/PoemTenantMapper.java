package cn.poem.solon.admin.tenant.mapper;

import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenant;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantPermissionGroupTableDef;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantTableDef;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantVo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;

import java.time.LocalDateTime;

/**
 * PoemTenant Mapper
 *
 * @Author hans
 */
public interface PoemTenantMapper extends BaseMapper<PoemTenant> {
    PoemTenantPermissionGroupTableDef POEM_TENANT_PERMISSION_GROUP= PoemTenantPermissionGroupTableDef.POEM_TENANT_PERMISSION_GROUP;

    /**
     * 分页sql
     * @param poemTenantPageDTO 分页条件
     */
    default Page<PoemTenantVo> page(PoemTenantPageDTO poemTenantPageDTO) {
        PoemTenantTableDef POEM_TENANT=PoemTenantTableDef.POEM_TENANT;
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_TENANT)
                .innerJoin(POEM_TENANT_PERMISSION_GROUP).on(POEM_TENANT.GROUP_ID.eq(POEM_TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(POEM_TENANT.TENANT_NAME.like(poemTenantPageDTO.getTenantName()))
                .orderBy(POEM_TENANT.UPDATE_TIME.desc());

        return paginateAs(poemTenantPageDTO.getPageNumber(),poemTenantPageDTO.getPageSize(),queryWrapper,PoemTenantVo.class);
    }

    /**
     * 租户详情sql
     * @param tenantId 租户id
     */
    default PoemTenantVo info(Long tenantId) {
        PoemTenantTableDef POEM_TENANT=PoemTenantTableDef.POEM_TENANT;
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_TENANT)
                .innerJoin(POEM_TENANT_PERMISSION_GROUP).on(POEM_TENANT.GROUP_ID.eq(POEM_TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(POEM_TENANT.TENANT_ID.eq(tenantId));

        return selectOneByQueryAs(queryWrapper,PoemTenantVo.class);
    }

    /**
     * 租户名是否存在
     * @param tenantName 租户名
     */
    default long selectCountByTenantName(String tenantName) {
        PoemTenantTableDef POEM_TENANT=PoemTenantTableDef.POEM_TENANT;
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_TENANT)
                .innerJoin(POEM_TENANT_PERMISSION_GROUP).on(POEM_TENANT.GROUP_ID.eq(POEM_TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(POEM_TENANT.TENANT_NAME.eq(tenantName));

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
        PoemTenant poemTenant = UpdateEntity.of(PoemTenant.class, tenantId)
                .setTenantName(tenantName)
                .setExpires(expires)
                .setGroupId(groupId);
        return update(poemTenant);
    }
}
