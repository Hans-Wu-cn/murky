package cn.poem.solon.utils;

import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.expand.SecurityCache;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.bean.InitializingBean;

/**
 *  SecurityCache的工具类，方便静态调用
 *
 * @author hans
 */
public class SecurityUtil implements InitializingBean {

    static SecurityCache<?> securityCache;


    static {
        Solon.context().getBeanAsync(SecurityCache.class,s->{
            securityCache=s;
        });
    }

    public static UserInfo getUserInfo(){
        return securityCache.getuserInfo();
    }

    public static void setUserInfo(UserInfo userInfo){
        securityCache.setUserInfo(userInfo);
    }

    public static void publishAsyncEvent(){
        securityCache.publishAsyncEvent();
    }
}
