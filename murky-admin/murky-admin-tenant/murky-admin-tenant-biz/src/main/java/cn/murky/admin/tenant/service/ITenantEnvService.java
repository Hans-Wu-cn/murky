package cn.murky.admin.tenant.service;

import cn.murky.admin.system.api.domian.bo.SysDictBO;
import cn.murky.admin.tenant.domain.entity.TenantEnv;
import com.mybatisflex.core.service.IService;
import org.noear.redisx.RedisClient;

import java.util.List;

public interface ITenantEnvService extends IService<TenantEnv> {
    /**
     * 获取租户所有redis环境客户端
     */
    List<RedisClient> getAllTenantRedisEnv();

}
