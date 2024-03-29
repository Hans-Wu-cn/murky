package cn.murky.tenant.system.api;

import cn.murky.tenant.system.api.domain.bo.SysRoleBO;

/**
 * 租户角色API
 */
public interface TenantRoleApi {

    /**
     * 根据角色id查询租户角色
     * @param fkRoleId 角色id
     */
    SysRoleBO getSysRoleById(Long fkRoleId);
}
