package cn.poem.solon.expand;

import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.event.PoemEventListener;
import cn.poem.solon.event.SecurityNotLoginEvent;

/**
 * 用户信息缓存类
 *
 * @author hans
 */
public interface SecurityCache<T extends SecurityNotLoginEvent> extends PoemEventListener<T>  {
    public static final String PREFIX_KEY="SECRUITY_CACHE_KEY:";

    /**
     * 缓存用户信息
     * @param userInfo 用户信息对象
     */
    public abstract void setUserInfo(UserInfo userInfo);

    /**
     * 获取缓存中的用户信息
     * @return 用户信息对象
     */
    public abstract UserInfo getuserInfo();

    /**
     * 推送事件
     */
    public abstract void publishAsyncEvent();


}
