package cn.poem.mybatisplus.handler;


import cn.poem.mybatisplus.extension.BaseEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Configuration;

import java.util.Date;

@Component
public class PoemMetaBaseEntityHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
            Date now = new Date();
            this.strictInsertFill(metaObject, "createTime", Date.class, now); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        this.strictInsertFill(metaObject, "updateTime", Date.class, now); // 起始版本 3.3.0(推荐使用)
    }
}
