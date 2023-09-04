package cn.poem.solon.utils;

import cn.poem.solon.expand.SecurityUserInfo;
import cn.poem.solon.expand.SystemSecurityCache;
import org.noear.solon.Solon;

public class SecurityUtils {
    static SystemSecurityCache securityCache;

    static {
        Solon.context().getBeanAsync(SystemSecurityCache.class,SystemSecurityCache ->{
            securityCache=SystemSecurityCache;
        });
    }

    /**
     * 获取当前登录用户ID
     * @return 用户id
     */
    public static Long getUserId(){
        return securityCache.getUserId();
    }

    /**
     * 获取当前登录用户信息
     * @return  当前登录用户信息
     */
    public static SecurityUserInfo getUserInfo(){
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

    public static Boolean isAdmin(){
        return securityCache.admin();
    }
}
