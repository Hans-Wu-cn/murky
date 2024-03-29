package cn.murky.admin.core.utils;


import cn.dev33.satoken.exception.NotLoginException;
import cn.murky.security.SecurityCache;
import cn.murky.security.entity.SecurityUserInfo;
import org.noear.redisx.RedisClient;
import org.noear.solon.Solon;


public class SecurityUtils {
    static SecurityCache<SecurityUserInfo> securityCache;
    static RedisClient redisClient;

    static {
        Solon.context().getBeanAsync(SecurityCache.class, systemSecurityCache ->{
            securityCache=systemSecurityCache;
        });
        Solon.context().getBeanAsync(RedisClient.class, bean ->{
            redisClient=bean;
        });
    }

    /**
     * 获取当前登录用户ID
     * @return 用户id
     */
    public static Long getUserId(){
        return getUserInfo().getUserId();
    }

    /**
     * 获取当前登录用户信息
     * @return  当前登录用户信息
     */
    public static SecurityUserInfo getUserInfo() throws NotLoginException {
        return securityCache.getUserInfo();
    }

    /**
     * 保存当前登录用户信息
     * @param securityUser 登录用户信息
     */
    public static void setUserInfo(SecurityUserInfo securityUser){
        securityCache.setUserInfo(securityUser);
    }

    /**
     * 修改当前登录用户信息
     */
    public static void delUserInfo(){
        securityCache.delUserInfo();
    }

    public static Boolean isAdmin() throws NotLoginException {
        return getUserInfo().getAdmin();
    }


}
