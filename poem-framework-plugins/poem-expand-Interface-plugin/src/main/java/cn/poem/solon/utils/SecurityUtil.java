package cn.poem.solon.utils;

import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.expand.SecurityCache;
import org.noear.solon.Solon;

/**
 *  SecurityCache的工具类，方便静态调用
 *
 * @author hans
 */
public class SecurityUtil {

    static SecurityCache<?> securityCache;


    static {
        Solon.context().getBeanAsync(SecurityCache.class,s->{
            securityCache=s;
        });
    }

    /**
     * 获取用户登录信息
     * @return 用户信息对象
     */
    public static UserInfo getUserInfo(){
        return securityCache.getUserInfo();
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public static Long getUserId(){
        return securityCache.getUserId();
    }


    /**
     * 用户是否是超级管理员
     * @return 用户id
     */
    public static Boolean admin(){
        return securityCache.getUserInfo().getAdmin();
    }

    /**
     * 修改缓存中用户的信息
     * @param userInfo
     */
    public static void setUserInfo(UserInfo userInfo){
        securityCache.setUserInfo(userInfo);
    }

    /**
     * 推送用户未登录事件
     */
    public static void publishAsyncEvent(){
        securityCache.publishAsyncEvent();
    }
}
