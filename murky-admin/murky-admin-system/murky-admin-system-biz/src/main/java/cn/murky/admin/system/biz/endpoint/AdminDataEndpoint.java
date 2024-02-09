package cn.murky.admin.system.biz.endpoint;

import cn.murky.admin.system.biz.convert.SysDictConvert;
import cn.murky.admin.system.biz.service.ISysDictDataService;
import cn.murky.common.domain.bo.SysDictDataBO;
import cn.murky.common.domain.sdo.SysDictDataSDO;
import cn.murky.socketd.MurkyEntity;
import cn.murky.socketd.listener.SystemDataEventListener;
import org.noear.socketd.transport.core.Message;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.transport.core.listener.MessageHandler;
import org.noear.solon.annotation.Inject;
import org.noear.solon.net.annotation.ServerEndpoint;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/admin/data")
public class AdminDataEndpoint extends SystemDataEventListener {
    @Inject
    private ISysDictDataService iSysDictDataService;
    public static Map<String,Session> sessionMap = new ConcurrentHashMap<>();

    public Collection<Session> getOpenSessions() {
        return sessionMap.values();
    }


    @Override
    public void onOpen(Session session) throws IOException {
        sessionMap.put(session.sessionId(), session);
    }

    @Override
    public MessageHandler onDict(Session session, Message message) throws IOException {
        List<SysDictDataBO> allDict = iSysDictDataService.getAllDict();
        List<SysDictDataSDO> data = SysDictConvert.INSTANCES.toSDO(allDict);
        session.reply(message,new MurkyEntity(data));
        return this::onMessage;
    }

    public void publishOnDict() throws IOException {
        List<SysDictDataBO> allDict = iSysDictDataService.getAllDict();
        List<SysDictDataSDO> data = SysDictConvert.INSTANCES.toSDO(allDict);
        MurkyEntity murkyEntity = new MurkyEntity(data);
        for (Session session : getOpenSessions()) {
            session.send(ON_DICT,murkyEntity);
        }
    }
}
