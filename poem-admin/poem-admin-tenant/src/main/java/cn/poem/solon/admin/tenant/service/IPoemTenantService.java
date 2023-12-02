package cn.poem.solon.admin.tenant.service;

import cn.poem.solon.admin.tenant.domain.dto.PoemTenantFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenant;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

/**
 * PoemTenant Service
 *
 * @Author hans
 */
public interface IPoemTenantService extends IService<PoemTenant> {

    /**
     * 分页接口
     *
     * @param poemTenantPageDTO 分页DTO对象
     */
    Page<PoemTenantVo> page(PoemTenantPageDTO poemTenantPageDTO);

    /**
     * 详情接口
     *
     * @param tenantId 租户id
     */
    PoemTenantVo info(Long tenantId);

    /**
     * 添加租户
     *
     * @param poemTenantFromDTO 租户表单对象
     */
    Boolean add(PoemTenantFromDTO poemTenantFromDTO);

    /**
     * 修改租户
     *
     * @param poemTenantFromDTO 租户表单对象
     */
    Boolean edit(PoemTenantFromDTO poemTenantFromDTO);
}
