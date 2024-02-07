package cn.murky.tenant.system.biz.service;

import cn.murky.tenant.system.biz.domian.entity.TenantEnv;
import com.mybatisflex.core.service.IService;
import org.noear.redisx.RedisClient;

import java.util.List;

public interface ITenantEnvService extends IService<TenantEnv> {

    /**
     * 根据租户获取指定redis客户端
     * @param tenantId 租户id
     * @return redis客户端
     */
    RedisClient getRedisClientByTenantId(Long tenantId);

    /**
     * 获取租户所有redis环境客户端
     */
    List<RedisClient> getAllTenantRedisEnv();

}
