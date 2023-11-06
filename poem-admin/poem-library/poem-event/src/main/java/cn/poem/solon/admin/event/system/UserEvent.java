package cn.poem.solon.admin.event.system;

import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.domin.PoemUser;
import org.noear.dami.solon.annotation.DamiTopic;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * 用户事件
 */
@DamiTopic("event.user")
public interface UserEvent {
    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    PoemUser getOneByAccount(String account);

    /**
     * 获取用户详情事件
     * @return
     */
    SecurityUserInfo userInfo() throws LoginException;
}
