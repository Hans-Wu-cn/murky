package cn.poem.solon.expand;

import cn.dev33.satoken.stp.StpUtil;
import cn.poem.SecurityCache;
import cn.poem.solon.expand.SecurityUserInfo;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.InitializingBean;

import java.util.Collections;

/**
 * 该系统的安全工具扩展
 *
 * @author hans
 */
@Component
public class SystemSecurityCache implements SecurityCache<SecurityUserInfo>{
    @Inject
    public RedisClient redisClient;

    private final String key="System:SecurityUser:";
    /**
     * 用户是否是超级管理员
     * @return 用户id
     */
    public Boolean admin(){
        return getUserInfo().getAdmin();
    }

    @Override
    public void setUserInfo(SecurityUserInfo securityUser) {
        redisClient.open(session -> {
            String serialize = ONode.serialize(securityUser);
            session.key(key+getUserId()).expire(7200).set(serialize);
            session.key("user_link:1").delay(20);
        });
    }

    @Override
    public SecurityUserInfo getUserInfo() {
        String json = redisClient.openAndGet(session -> session.key(key+getUserId()).get());
        return ONode.deserialize(json);
    }

    @Override
    public void delUserInfo() {
        redisClient.open(session -> {
            session.deleteKeys(Collections.singleton(key + getUserId()));
        });
    }

    @Override
    public Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }

}
