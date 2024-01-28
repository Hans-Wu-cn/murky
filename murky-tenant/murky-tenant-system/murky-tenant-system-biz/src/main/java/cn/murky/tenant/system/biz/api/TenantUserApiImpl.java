package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantUserApi;
import cn.murky.tenant.system.api.domain.bo.TenantUserBO;
import org.noear.solon.annotation.Component;

/**
 * 租户用户api
 */
@Component
public class TenantUserApiImpl implements TenantUserApi {
    @Override
    public TenantUserBO getById(Long id) {
        return null;

    }

    @Override
    public TenantUserBO getOneByAccount(String account) {
        return null;

    }
}
