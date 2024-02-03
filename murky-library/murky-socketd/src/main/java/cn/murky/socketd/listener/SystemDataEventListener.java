package cn.murky.socketd.listener;

import cn.murky.socketd.MurkyEventListener;
import cn.murky.socketd.annotation.SdEvent;
import org.noear.socketd.transport.core.Message;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.transport.core.listener.MessageHandler;

import java.io.IOException;

public abstract class SystemDataEventListener extends MurkyEventListener {
    protected static final String ON_DICT="onDict";
    /**
     * 连接初始化事件
     *
     * @param session
     * @throws IOException
     */
    @SdEvent
    public abstract MessageHandler onDict(Session session, Message message) throws IOException;

}
