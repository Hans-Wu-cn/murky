package cn.murky.admin.tenant.service.impl;

import cn.murky.admin.system.api.domian.bo.SysDictBO;
import cn.murky.admin.tenant.api.enums.EnvTypeEnum;
import cn.murky.admin.tenant.domain.entity.TenantEnv;
import cn.murky.admin.tenant.mapper.TenantEnvMapper;
import cn.murky.admin.tenant.service.ITenantEnvService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.RedisClient;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TenantEnvServiceImpl extends ServiceImpl<TenantEnvMapper, TenantEnv> implements ITenantEnvService {
    private static final ConcurrentMap<Long, RedisClient> TENANT_ENV_REDIS = new ConcurrentHashMap<>();


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
//            Properties properties = new Properties();
            RedisClient redisClient = new RedisClient(properties);
            TENANT_ENV_REDIS.put(item.getFkTenantId(),redisClient);
        });
    }
}
