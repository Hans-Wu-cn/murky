package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantUserApi;
import cn.murky.tenant.system.api.domain.bo.TenantUserBO;
import cn.murky.tenant.system.biz.convert.TenantUserConvert;
import cn.murky.tenant.system.biz.domian.entity.TenantUser;
import cn.murky.tenant.system.biz.mapper.TenantUserMapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

/**
 * 租户用户api
 */
@Component
public class TenantUserApiImpl implements TenantUserApi {
    @Inject
    private TenantUserMapper tenantUserMapper;
    @Override
    public TenantUserBO getById(Long id) {
        TenantUser tenantUser = tenantUserMapper.selectOneById(id);
        return TenantUserConvert.INSTANCES.toBO(tenantUser);

    }

    @Override
    public TenantUserBO getOneByAccount(String account) {
        TenantUser tenantUser = tenantUserMapper.selectByAccount(account);
        return TenantUserConvert.INSTANCES.toBO(tenantUser);
    }
}
