package cn.poem.solon.event;

/**
 * 用户信息缓存事件
 *
 * @author hans
 */
public abstract class SecurityNotLoginEvent extends PoemEvent{
    public SecurityNotLoginEvent(Object data) {
        super(data);
    }
}
