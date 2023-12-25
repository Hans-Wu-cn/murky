package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.contant.SystemParameterContant;
import cn.poem.solon.admin.system.domain.entity.SystemParameter;
import cn.poem.solon.admin.system.mapper.SystemParameterMapper;
import cn.poem.solon.admin.system.service.ISystemParameterService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.internal.util.Strings;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SystemParameterService
 *
 * @Author hans
 */
@Component
@Slf4j
public class ISystemParameterServiceImpl extends ServiceImpl<SystemParameterMapper, SystemParameter> implements ISystemParameterService {

    @Inject
    private RedisClient redisClient;


    /**
     * 刷新缓存
     */
    @Override
    public void refresh() {
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
        redisHash.clear();
        List<SystemParameter> systemParameters = mapper.selectAll();
        for (SystemParameter systemParameter : systemParameters) {
            redisHash.putAndSerialize(systemParameter.getKey(), systemParameter.getValue());
        }
        log.info("初始化系统配置缓存");
    }

    /**
     * 根据参数key获取默认配置
     */
    @Override
    public String getDefaultByKey(String key) {
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
        String defaultPassword = redisHash.get(key);
        if (!Strings.isEmpty(defaultPassword)) {
            return defaultPassword;
        }
        SystemParameter systemParameter = mapper.selectByKey(key);
        redisHash.putAndSerialize(systemParameter.getKey(), systemParameter.getValue());
        return systemParameter.getValue();
    }

    /**
     * 获取默认密码
     * @return 密码
     */
    @Override
    public String getDefaultUserPassword() {
        String key = SystemParameterContant.DEFAULT_USER_PASSWORD;
        String defaultPassword = getDefaultByKey(key);
        if (!Strings.isEmpty(defaultPassword)) {
            return defaultPassword;
        }
        return SystemParameterContant.DEFAULT_PASSWORD;
    }

    /**
     * 初始化缓存
     */
    @Init
    public void initParameter() {
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
        // 如果已经被初始化过则不需要在初始化
        if (!redisHash.isEmpty()) {
            return;
        }
        List<SystemParameter> systemParameters = mapper.selectAll();
        Map<String, String> map = new HashMap<>();
        for (SystemParameter systemParameter : systemParameters) {
            map.put(systemParameter.getKey(), systemParameter.getValue());
        }
        redisHash.putAll(map);
        log.info("初始化系统配置缓存");
    }
}
