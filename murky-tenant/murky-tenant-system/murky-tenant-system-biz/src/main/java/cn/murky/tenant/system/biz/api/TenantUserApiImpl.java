package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantUserApi;
import cn.murky.tenant.system.api.domain.bo.TenantUserBO;
import cn.murky.tenant.system.biz.convert.TenantUserConvert;
import cn.murky.tenant.system.biz.domian.entity.TenantUser;
import org.noear.solon.annotation.Component;

/**
 * 租户用户api
 */
@Component
public class TenantUserApiImpl implements TenantUserApi {
    @Override
    public TenantUserBO getById(Long id) {
        TenantUser tenantUser = new TenantUser().where(TenantUser::getId).eq(id).oneById();
        return TenantUserConvert.INSTANCES.toBO(tenantUser);

    }

    @Override
    public TenantUserBO getOneByAccount(String account) {
        TenantUser tenantUser = new TenantUser().where(TenantUser::getAccount).eq(account).one();
        return TenantUserConvert.INSTANCES.toBO(tenantUser);
    }
}
