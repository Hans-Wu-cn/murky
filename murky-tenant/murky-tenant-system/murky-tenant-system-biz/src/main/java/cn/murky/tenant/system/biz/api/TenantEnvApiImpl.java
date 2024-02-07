package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantEnvApi;
import cn.murky.tenant.system.biz.service.ITenantEnvService;
import org.noear.redisx.RedisClient;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class TenantEnvApiImpl implements TenantEnvApi {
    @Inject
    private ITenantEnvService iTenantEnvService;
    @Override
    public RedisClient getRedisClientByTenantId(Long tenantId) {
        return iTenantEnvService.getRedisClientByTenantId(tenantId);
    }
}
