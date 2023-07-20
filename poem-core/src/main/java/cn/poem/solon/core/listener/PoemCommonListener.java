package cn.poem.solon.core.listener;

import cn.poem.solon.core.enetity.BaseEntity;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.SetListener;
import com.mybatisflex.annotation.UpdateListener;

import java.util.Date;

public class PoemCommonListener implements UpdateListener, InsertListener {
    @Override
    public void onInsert(Object entity) {
        BaseEntity baseEntity=(BaseEntity) entity;
        Date now = new Date();
        baseEntity.setCreateTime(now);
    }

    @Override
    public void onUpdate(Object entity) {
        BaseEntity baseEntity=(BaseEntity) entity;
        Date now = new Date();
        baseEntity.setUpdateTime(now);
    }
}
