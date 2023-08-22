package cn.poem.solon.mybatisflex.listener;

import cn.poem.solon.mybatisflex.extension.BaseEntity;
import cn.poem.solon.utils.SecurityUtil;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import org.noear.solon.Solon;

import java.time.LocalDateTime;

/**
 * mybaits-flex字段填充器
 *
 * @author hans
 */
public class PoemInsertListener implements InsertListener, UpdateListener {

    /**
     * 新增sql 字段填充
     * @param o sql对象
     */
    @Override
    public void onInsert(Object o) {
        if(o instanceof BaseEntity baseEntity){
            baseEntity.setCreateTime(LocalDateTime.now());
            baseEntity.setCreateUser(SecurityUtil.getUserId());
        }
    }

    /**
     * 修改sql 字段填充
     * @param o sql对象
     */
    @Override
    public void onUpdate(Object o) {
        if(o instanceof BaseEntity baseEntity){
            baseEntity.setUpdateTime(LocalDateTime.now());
            baseEntity.setUpdateUser(SecurityUtil.getUserId());

        }
    }
}
