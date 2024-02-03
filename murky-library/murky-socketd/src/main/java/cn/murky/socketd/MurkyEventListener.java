package cn.murky.socketd;

import cn.murky.common.utils.StringUtils;
import cn.murky.socketd.annotation.SdEvent;
import org.noear.socketd.transport.core.Message;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.transport.core.listener.EventListener;
import org.noear.socketd.transport.core.listener.MessageHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class MurkyEventListener extends EventListener {

    private static final Map<String, Method> EVENT_METHOD_MAP = new HashMap();

    /**
     * 监听路径
     *
     * @return
     */
    public String getPath() {
        return "";
    }


    /**
     * 为了服务端能更好的挂载事件
     *
     * @param session 会话
     * @param message 消息
     * @throws IOException
     */
    @Override
    public void onMessage(Session session, Message message) throws IOException {
        mountEvent(session, message);
    }

    /**
     * 挂载事件
     *
     * @param session
     * @throws IOException
     */
    protected void mountEvent(Session session, Message message) throws IOException {
        if (EVENT_METHOD_MAP.isEmpty()) {
            Method[] superClassMethod = this.getClass().getSuperclass().getDeclaredMethods();
            for (Method method : superClassMethod) {
                SdEvent sdEventAno = method.getDeclaredAnnotation(SdEvent.class);
                if (sdEventAno != null) {
                    String event = sdEventAno.eventName();
                    if (StringUtils.isEmpty(event)) {
                        event = method.getName();
                    }
                    EVENT_METHOD_MAP.put(event, method);
                }
            }
        }
        try {
            Method method = EVENT_METHOD_MAP.get(message.event());
            super.doOn(message.event(), (MessageHandler) method.invoke(this, session, message));
//            super.doOnMessage((MessageHandler) method.invoke(this, session, message));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
