package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.contant.SystemParameterContant;
import cn.poem.solon.admin.system.domain.entity.SystemParameter;
import cn.poem.solon.admin.system.mapper.SystemParameterMapper;
import cn.poem.solon.admin.system.service.ISystemParameterService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * SystemParameterService
 * @Author hans
 */
@Component
@Slf4j
public class ISystemParameterServiceImpl extends ServiceImpl<SystemParameterMapper, SystemParameter> implements ISystemParameterService {

    @Inject
    private RedisClient redisClient;

    private static final String KEY="123456";
    /**
     * 刷新缓存
     */
    @Init
    public void refresh(){
        RedisHash redisHash = redisClient.getHash(SystemParameterContant.PARAMETER_CACHE_KEY);
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
            return KEY;
        }
        return systemParameter.getValue();
    }
}
