package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantRoleApi;
import cn.murky.tenant.system.api.domain.bo.TenantRoleBO;
import org.noear.solon.annotation.Component;

/**
 * 租户角色API
 */
@Component
public class TenantRoleApiImpl implements TenantRoleApi {

    @Override
    public TenantRoleBO getTenantRoleById(Long fkRoleId){
        return null;

    }
}
