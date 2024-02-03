package cn.murky.tenant.system.biz.listener;

import cn.murky.common.sdo.SysDictDataSDO;
import cn.murky.socketd.MurkyEntity;
import cn.murky.socketd.listener.SystemDataEventListener;
import cn.murky.tenant.core.utils.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.noear.socketd.transport.core.Entity;
import org.noear.socketd.transport.core.Message;
import org.noear.socketd.transport.core.Reply;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.transport.core.listener.MessageHandler;
import org.noear.solon.annotation.Component;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AdminDataListener extends SystemDataEventListener {
    private static final String PATH = "/admin/data";
    @Override
    public void onOpen(Session session) throws IOException {
        System.out.println("onOpen");
        // 加载字典数据
        initDict(session);
    }

    @Override
    public void onClose(Session session) {
        System.out.printf("onClose", session);
    }

    @Override
    public void onError(Session session, Throwable error) {
        System.out.println("onError"+session);

    }

    @Override
    public MessageHandler onDict(Session session, Message message) throws IOException {
        initDict(message.entity());
        return this::onMessage;
    }

    @Override
    public String getPath() {
        return PATH;
    }


    /**
     * 初始化字典
     */
    private void initDict(Session session) throws IOException {
        Reply reply = session.sendAndRequest(ON_DICT, new MurkyEntity()).await();
        initDict(reply);
    }

    /**
     * 初始化字典
     */
    private void initDict(Entity entity){
        MurkyEntity murkyEntity = MurkyEntity.of(entity);
        List<SysDictDataSDO> sysDictDataSDOS = murkyEntity.dataAsListClass(SysDictDataSDO.class);
        Map<String, List<SysDictDataSDO>> dictMap = sysDictDataSDOS.stream()
                .collect(Collectors.groupingBy(SysDictDataSDO::getDictType));
        DictUtils.refresh(dictMap);
        log.info("[AdminDataListener] initDict -> 更新字典信息:{}",dictMap);
    }
}
