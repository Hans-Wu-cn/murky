package cn.poem.solon.satoken.event;

import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.event.SecurityNotLoginEvent;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;

@Component
@Singleton(false)
public class LocalSecuritNotLoginEvent extends SecurityNotLoginEvent {
    public LocalSecuritNotLoginEvent(Object data) {
        super(data);
    }

    @Override
    public UserInfo getData() {
        return (UserInfo) super.getData();
    }

    /**
     * 方便solon实例化
     */
    public LocalSecuritNotLoginEvent() {
        super(null);
    }
}
