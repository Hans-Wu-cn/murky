package cn.murky.tenant.core.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.murky.tenant.system.api.TenantEnvApi;
import org.noear.redisx.RedisClient;
import org.noear.solon.Solon;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static cn.murky.tenant.core.constant.Constants.TENANT_ID_HEADER;

/**
 * 在租户端不同租户使用的redis服务可能不一致
 * 租户端所有租户的redisClient统一在此处获取
 */
public class RedisUtils {
    private static final Map<Long, RedisClient> REDIS_CLIENT_MAP = new ConcurrentHashMap<>();
    private static TenantEnvApi tenantEnvApi;

    static {
        Solon.context().getBeanAsync(TenantEnvApi.class,bean->{
            tenantEnvApi=bean;
        });
    }

    /**
     * 获取指定租户的redisClient
     * 如果该租户没有配置其他的redis客户端,则使用系统默认配置
     * @param tenantId 租户id
     * @return redisClient
     */
    public static RedisClient get(Long tenantId) {
        return Optional.ofNullable(tenantEnvApi.getRedisClientByTenantId(tenantId)).
                orElseGet(() -> Solon.context().getBean(RedisClient.class));
    }

    /**
     * 获取当前租户的redisClient
     * 如果该租户没有配置其他的redis客户端,则使用系统默认配置
     * @return reidsClient
     */
    public static RedisClient get() {
        return get(SecurityUtils.getTenantId());
    }

}
