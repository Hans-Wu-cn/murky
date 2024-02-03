package cn.murky.socketd;

import org.noear.snack.ONode;
import org.noear.socketd.transport.core.Entity;
import org.noear.socketd.transport.core.entity.EntityDefault;
import org.w3c.dom.Node;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * MurkyEntity为了方便socketd交互传输数据使用
 * 默认的StringEntity每次使用都需要转为json过于繁琐
 */
public class MurkyEntity extends EntityDefault {
    private ONode data;

    public MurkyEntity(Object obj) {
        data = ONode.load(obj);
        dataSet(ONode.serialize(data).getBytes(StandardCharsets.UTF_8));
    }


    public MurkyEntity() {
    }

    public static MurkyEntity of(Entity entity) {
        String str = entity.dataAsString();
        MurkyEntity murkyEntity = new MurkyEntity();
        murkyEntity.data = ONode.loadStr(str);
        return murkyEntity;
    }

    public <T> T dataAsClass(Class<T> clz) {
        return data.toObject(clz);
    }

    public <T> List<T> dataAsListClass(Class<T> clz) {
        return data.toObjectList(clz);
    }

}
