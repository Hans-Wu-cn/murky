package cn.murky.security;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.murky.common.constant.BusTopicConstant;
import cn.murky.security.entity.SecurityUserInfo;
import org.noear.dami.Dami;
import org.noear.redisx.RedisClient;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;

import java.util.Collections;

/**
 * 该系统的安全工具扩展
 *
 * @author hans
 */
@Component
public class SecurityCache <T extends SecurityUser> {
    @Inject
    private RedisClient redisClient;

    @Inject("${sa-token.timeout}")
    private static int expire = 0;

    private final String USER_KEY = "System:auth:user";
    private final String MENU_KEY = "System:auth:menu";


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
    public void setUserInfo(T securityUser) {
        redisClient.open(session -> {
            String serialize = ONode.stringify(securityUser);
            session.key(USER_KEY + getUserId()).expire(expire).set(serialize);
        });
    }

    /**
     * 获取缓存中的用户信息
     *
     * @return 用户信息对象
     */
    public T getUserInfo() throws NotLoginException {
        String json = redisClient.openAndGet(session -> session.key(USER_KEY + getUserId()).get());
        return ONode.deserialize(json, SecurityUserInfo.class);
    }

    /**
     * 删除用户信息
     */
    public void delUserInfo() {
        redisClient.open(session -> {
            session.deleteKeys(Collections.singleton(USER_KEY + getUserId()));
        });
    }

    /**
     * 获取用户id
     *
     * @return 返回用户id
     */
    public Long getUserId() throws NotLoginException{
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 缓存用户菜单
     */
    public void delUserMenu() throws NotLoginException {
        redisClient.open(session -> {
            session.deleteKeys(Collections.singleton(MENU_KEY + getUserId()));
        });
    }


    /**
     * 1.同步配置文件参数
     * 2.挂在damiBus事件
     *
     */
    @Init
    public void init(){
////        String s = Solon.cfg().get("sa-token.timeout");
////        expire = Integer.parseInt(s);
//        //挂载获取用户i事件
//        Dami.<String, Long>bus().listen(BusTopicConstant.USER_ID_TOPIC, payload -> {
//            if (payload.isRequest()) {
//                payload.reply(this.getUserId()); // sendAndResponse 只接收第一个
//            }
//        });
//
//        //挂载获取用户详情事件
//        Dami.<String, SecurityUserInfo>bus().listen(BusTopicConstant.USER_INFO_TOPIC, payload -> {
//            if (payload.isRequest()) {
//                payload.reply(this.getUserInfo()); // sendAndResponse 只接收第一个
//            }
//        });
    }
}
