package cn.poem.mybatisflex.listener;

import cn.poem.mybatisflex.extension.BaseEntity;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;

import java.util.Date;

public class PoemInsertListener implements InsertListener, UpdateListener {
    @Override
    public void onInsert(Object o) {
        if(o instanceof BaseEntity){
            //todo 填充字段
            BaseEntity baseEntity=(BaseEntity) o;
            baseEntity.setCreateTime(new Date());
        }
    }

    @Override
    public void onUpdate(Object o) {
        if(o instanceof BaseEntity){
            //todo 填充字段
            BaseEntity baseEntity=(BaseEntity) o;
            baseEntity.setUpdateTime(new Date());
        }
    }
}
