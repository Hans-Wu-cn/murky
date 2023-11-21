package cn.poem.solon.admin.event.system;

import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.domin.PoemUser;
import org.noear.dami.solon.annotation.DamiTopic;

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
    SecurityUserInfo userInfo();

    /**
     * 设置用户语言偏好
     * @return
     */
    boolean setLanguage(String language);
}
