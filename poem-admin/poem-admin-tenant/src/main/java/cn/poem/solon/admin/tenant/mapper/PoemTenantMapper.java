package cn.poem.solon.admin.tenant.mapper;

import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenant;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantPermissionGroupTableDef;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantTableDef;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantVo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * PoemTenant Mapper
 *
 * @Author hans
 */
public interface PoemTenantMapper extends BaseMapper<PoemTenant> {
    PoemTenantTableDef POEM_TENANT=PoemTenantTableDef.POEM_TENANT;
    PoemTenantPermissionGroupTableDef POEM_TENANT_PERMISSION_GROUP= PoemTenantPermissionGroupTableDef.POEM_TENANT_PERMISSION_GROUP;

    /**
     * 分页sql
     * @param poemTenantPageDTO 分页条件
     */
    default Page<PoemTenantVo> page(PoemTenantPageDTO poemTenantPageDTO) {
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
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_TENANT)
                .innerJoin(POEM_TENANT_PERMISSION_GROUP).on(POEM_TENANT.GROUP_ID.eq(POEM_TENANT_PERMISSION_GROUP.GROUP_ID))
                .where(POEM_TENANT.TENANT_ID.eq(tenantId));

        return selectOneByQueryAs(queryWrapper,PoemTenantVo.class);
    }
}
