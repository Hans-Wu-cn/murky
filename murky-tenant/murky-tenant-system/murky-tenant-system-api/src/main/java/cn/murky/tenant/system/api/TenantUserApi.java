package cn.murky.tenant.system.api;

import cn.murky.tenant.system.api.domain.bo.TenantUserBO;

/**
 * 租户用户api
 */
public interface TenantUserApi {
    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     */
    TenantUserBO getById(Long id);

    /**
     * 根据用户账号查询用户信息
     * @param account 账号
     */
    TenantUserBO getOneByAccount(String account);
}
