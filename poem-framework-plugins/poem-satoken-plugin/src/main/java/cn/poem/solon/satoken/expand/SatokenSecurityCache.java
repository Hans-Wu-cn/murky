package cn.poem.solon.satoken.expand;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.expand.SecurityCache;
import cn.poem.solon.satoken.event.LocalSecuritNotLoginEvent;
import cn.poem.solon.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.bean.InitializingBean;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * satoken 用户信息缓存类 本地缓存实现
 *
 * @author hans
 */
@Component
@Slf4j
public class SatokenSecurityCache implements InitializingBean, SecurityCache<LocalSecuritNotLoginEvent> {

    private ConcurrentHashMap<String, UserInfo> userInfoCachePool=new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,String> userIdCachePool=new ConcurrentHashMap<>();


    /**
     * 缓存用户信息
     * @param userInfo 用户信息对象
     */
    @Override
    public void setUserInfo(UserInfo userInfo) {
        userInfoCachePool.put(PREFIX_KEY + StpUtil.getLoginIdAsString(),userInfo);
        userIdCachePool.put(PREFIX_KEY + StpUtil.getTokenValue(),StpUtil.getLoginIdAsString());
    }

    /**
     * 获取缓存中的用户信息
     * @return 用户信息对象
     */
    @Override
    public UserInfo getUserInfo() {
        return userInfoCachePool.get(PREFIX_KEY + StpUtil.getLoginIdAsString());
    }

    @Override
    public Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 推送事件
     */
    @Override
    public void publishAsyncEvent() {
        UserInfo userInfo=null;
        try {
            userInfo = this.getUserInfo();
        }catch (NotLoginException exception){
            String userId = userIdCachePool.get(PREFIX_KEY + StpUtil.getTokenValue());
            userInfo = userInfoCachePool.get(PREFIX_KEY + userId);

        }
        EventBus.publishAsync(new LocalSecuritNotLoginEvent(userInfo));
    }

    /**
     * 订阅本地缓存登录异常事件
     * @param localSecurityCacheEvent 本地安全缓存事件
     * @throws Throwable
     */
    @Override
    public void onEvent(LocalSecuritNotLoginEvent localSecurityCacheEvent) throws Throwable {
        log.debug("触发退出登录事件");
        UserInfo data = localSecurityCacheEvent.getData();
        if(data != null){
            userInfoCachePool.remove(PREFIX_KEY + data.getUserId());
            userIdCachePool.remove(PREFIX_KEY + StpUtil.getTokenValue());
            StpUtil.logout(data.getUserId());
        }
    }

    @Override
    public void afterInjection() throws Throwable{
        //订阅该事件
        EventBus.subscribe(LocalSecuritNotLoginEvent.class,this);
    }
}
