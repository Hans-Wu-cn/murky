package cn.poem.solon.admin.event.system;

import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.event.system.entity.PoemUserRole;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.dami.solon.annotation.DamiTopic;

import java.util.List;

/**
 * 用户事件
 */
@DamiTopic("event.user")
public interface UserEvent {
    PoemUser getOne(String account,String password);

    /**
     * 获取用户详情事件
     * @return
     */
    SecurityUserInfo userInfo();
}
