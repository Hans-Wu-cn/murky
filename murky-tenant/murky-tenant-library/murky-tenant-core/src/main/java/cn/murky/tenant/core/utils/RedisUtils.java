package cn.murky.tenant.core.utils;

import org.noear.redisx.RedisClient;
import org.noear.solon.Solon;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在租户端不同租户使用的redis服务可能不一致
 * 租户端所有租户的redisClient统一在此处获取
 */
public class RedisUtils {
    private static final Map<Long, RedisClient> REDIS_CLIENT_MAP = new ConcurrentHashMap<>();

    /**
     * 为指定租户设置reidsClient
     *
     * @param tenantId    租户id
     * @param redisClient redisClient
     */
    public static void put(Long tenantId, RedisClient redisClient) {
        REDIS_CLIENT_MAP.put(tenantId, redisClient);
    }

    /**
     * 获取指定租户的redisClient
     * 如果该租户没有配置其他的redis客户端,则使用系统默认配置
     * @param tenantId 租户id
     * @return redisClient
     */
    public static RedisClient get(Long tenantId) {
        return Optional.ofNullable(REDIS_CLIENT_MAP.get(tenantId)).
                orElseGet(() -> Solon.context().getBean(RedisClient.class));
    }

    /**
     * 获取当前租户的redisClient
     * 如果该租户没有配置其他的redis客户端,则使用系统默认配置
     * @return reidsClient
     */
    public static RedisClient get() {
        return get(SecurityUtils.getUserInfo().getTenantId());
    }

}
