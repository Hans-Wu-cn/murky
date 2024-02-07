package cn.murky.tenant.system.api;

import org.noear.redisx.RedisClient;

public interface TenantEnvApi {
    /**
     * 根据租户获取指定redis客户端
     * @param tenantId 租户id
     * @return redis客户端
     */
    RedisClient getRedisClientByTenantId(Long tenantId);
}
