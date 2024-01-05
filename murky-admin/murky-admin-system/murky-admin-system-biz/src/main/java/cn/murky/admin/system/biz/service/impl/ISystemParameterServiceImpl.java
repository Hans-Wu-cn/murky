package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.biz.contant.SystemParameterContant;
import cn.murky.admin.system.biz.domain.entity.SystemParameter;
import cn.murky.admin.system.biz.mapper.SystemParameterMapper;
import cn.murky.admin.system.biz.service.ISystemParameterService;
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
    public void refresh(){
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
        redisHash.clear();
        List<SystemParameter> systemParameters = mapper.selectAll();
        for (SystemParameter systemParameter : systemParameters) {
            redisHash.putAndSerialize(systemParameter.getKey(),systemParameter.getValue());
        }
        log.info("初始化系统配置缓存");
    }

    @Override
    public String getDefaultUserPassword() {
        String key=SystemParameterContant.DEFAULT_USER_PASSWORD;
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
        String defaultPassword = redisHash.get(key);
        if(!Strings.isEmpty(defaultPassword)){
            return defaultPassword;
        }
        SystemParameter systemParameter = mapper.selectByKey(key);
        if(systemParameter==null || Strings.isEmpty(systemParameter.getKey())){
            return SystemParameterContant.DEFAULT_PASSWORD;
        }
        return systemParameter.getValue();
    }

    /**
     * 初始化缓存
     */
    @Init
    public void initParameter(){
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
        // 如果已经被初始化过则不需要在初始化
        if(!redisHash.isEmpty()){
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
