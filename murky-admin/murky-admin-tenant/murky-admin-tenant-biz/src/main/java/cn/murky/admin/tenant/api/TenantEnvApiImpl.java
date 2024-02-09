package cn.murky.admin.tenant.api;

import cn.murky.admin.system.api.constant.DictContant;
import cn.murky.common.domain.bo.SysDictBO;
import cn.murky.admin.tenant.service.ITenantEnvService;
import lombok.extern.slf4j.Slf4j;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

@Component
@Slf4j
public class TenantEnvApiImpl implements TenantEnvApi{
    @Inject
    private ITenantEnvService iTenantEnvService;
    @Override
    public void refreshDict(List<SysDictBO> sysDictBos) {
        List<RedisClient> allTenantRedisEnv = iTenantEnvService.getAllTenantRedisEnv();
        for (RedisClient redisClient : allTenantRedisEnv) {
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            log.info("[TenantEnvApiImpl] -> 初始化租户字典数据:{}",redisClient);
            for (SysDictBO sysDictBo : sysDictBos) {
                redisHash.putAndSerialize(sysDictBo.getDictType(), sysDictBo.getSysDictDataList());
            }
        }

    }
}
