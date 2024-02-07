package cn.murky.tenant.system.biz.service.impl;

import cn.murky.tenant.system.api.enums.EnvTypeEnum;
import cn.murky.tenant.system.biz.domian.entity.TenantEnv;
import cn.murky.tenant.system.biz.mapper.TenantEnvMapper;
import cn.murky.tenant.system.biz.service.ITenantEnvService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.RedisClient;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TenantEnvServiceImpl extends ServiceImpl<TenantEnvMapper, TenantEnv> implements ITenantEnvService {
    private static final ConcurrentMap<Long, RedisClient> TENANT_ENV_REDIS = new ConcurrentHashMap<>();


    @Override
    public RedisClient getRedisClientByTenantId(Long tenantId) {
        return TENANT_ENV_REDIS.get(tenantId);
    }

    @Override
    public List<RedisClient> getAllTenantRedisEnv() {
        return TENANT_ENV_REDIS.values().stream().toList();
    }

    @Init
    public void init() {
        List<TenantEnv> tenantEnvs = mapper.selectByEnvType(EnvTypeEnum.REDIS);
        tenantEnvs.forEach(item->{
            ONode oNode = ONode.loadStr(item.getEnvConfig());
            Properties properties = oNode.toObject(Properties.class);
            RedisClient redisClient = new RedisClient(properties);
            TENANT_ENV_REDIS.put(item.getFkTenantId(),redisClient);
        });
    }
}
