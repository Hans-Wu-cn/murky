package cn.poem.solon.admin;

import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.entity.SecurityUserInfo;
import org.noear.dami.Dami;
import org.noear.redisx.RedisClient;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
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
public class SecurityCache implements InitializingBean {
    @Inject
    public RedisClient redisClient;

    private static int expire = 0;

    private final String key = "System:SecurityUser:";

    private final String topic = "SecurityUserInfo";

    /**
     * 用户是否是超级管理员
     *
     * @return 用户id
     */
    public Boolean admin() {
        return getUserInfo().getAdmin();
    }

    /**
     * 缓存用户信息
     *
     * @param securityUser 用户信息对象
     */
    public void setUserInfo(SecurityUserInfo securityUser) {
        redisClient.open(session -> {
            String serialize = ONode.serialize(securityUser);
            session.key(key + getUserId()).expire(expire).set(serialize);
        });
    }

    /**
     * 获取缓存中的用户信息
     *
     * @return 用户信息对象
     */
    public SecurityUserInfo getUserInfo() {
        String json = redisClient.openAndGet(session -> session.key(key + getUserId()).get());
        return ONode.deserialize(json);
    }

    /**
     * 删除用户信息
     */
    public void delUserInfo() {
        redisClient.open(session -> {
            session.deleteKeys(Collections.singleton(key + getUserId()));
        });
    }

    /**
     * 获取用户id
     *
     * @return 返回用户id
     */
    public Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }


    public void afterInjection() throws Throwable {
        String s = Solon.cfg().get("sa-token.timeout");
        expire = Integer.parseInt(s);
        Dami.<String, Long>bus().listen(topic, payload -> {
            if (payload.isRequest()) {
                payload.reply(this.getUserId()); // sendAndResponse 只接收第一个
            }
        });
    }
}
