package cn.poem.solon.admin;

import cn.poem.solon.admin.common.constant.BusTopicConstant;
import cn.poem.solon.admin.domin.BaseEntity;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.core.dialect.KeywordWrap;
import com.mybatisflex.core.dialect.LimitOffsetProcessor;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import org.noear.dami.Dami;

import java.time.LocalDateTime;

/**
 * mybaits-flex字段填充器
 *
 * @author hans
 */
public class PoemFlexImpl extends CommonsDialectImpl implements InsertListener, UpdateListener {

    public PoemFlexImpl() {
        super(KeywordWrap.DOUBLE_QUOTATION, LimitOffsetProcessor.POSTGRESQL);
    }

    /**
     * 新增sql 字段填充
     *
     * @param o sql对象
     */
    @Override
    public void onInsert(Object o) {
        if (o instanceof BaseEntity baseEntity) {
            baseEntity.setCreateTime(LocalDateTime.now());
            baseEntity.setCreateUser(getUserId());
        }
    }

    /**
     * 修改sql 字段填充
     *
     * @param o sql对象
     */
    @Override
    public void onUpdate(Object o) {
        if (o instanceof BaseEntity baseEntity) {
            baseEntity.setUpdateTime(LocalDateTime.now());
            baseEntity.setUpdateUser(getUserId());

        }
    }

    private Long getUserId(){
        return Dami.<String, Long>bus().sendAndRequest(BusTopicConstant.USER_ID_TOPIC,null);
    }
}
