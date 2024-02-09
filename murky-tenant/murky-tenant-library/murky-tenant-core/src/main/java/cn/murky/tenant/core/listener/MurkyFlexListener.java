package cn.murky.tenant.core.listener;

import cn.murky.common.domain.entity.BaseEntity;
import cn.murky.flex.FlexListener;
import cn.murky.tenant.core.utils.SecurityUtils;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.dialect.KeywordWrap;
import com.mybatisflex.core.dialect.LimitOffsetProcessor;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import org.noear.solon.annotation.Component;

import java.time.OffsetDateTime;

@Component
public class MurkyFlexListener extends CommonsDialectImpl implements InsertListener, UpdateListener, FlexListener {

    public MurkyFlexListener() {
        super(KeywordWrap.DOUBLE_QUOTATION, LimitOffsetProcessor.POSTGRESQL);
    }

    /**
     * 新增sql 字段填充
     *
     * @param o sql对象
     */
    @Override
    public void onInsert(Object o) {
        if (o instanceof BaseEntity<?> baseEntity) {
            baseEntity.setCreateTime(OffsetDateTime.now());
            baseEntity.setCreateUser(SecurityUtils.getUserId());
        }
    }

    /**
     * 修改sql 字段填充
     *
     * @param o sql对象
     */
    @Override
    public void onUpdate(Object o) {
        if (o instanceof BaseEntity<?> baseEntity) {
            baseEntity.setUpdateTime(OffsetDateTime.now());
            baseEntity.setUpdateUser(SecurityUtils.getUserId());

        }
    }

    /**
     * 注册
     * @param globalConfig
     */
    @Override
    public void registerListener(FlexGlobalConfig globalConfig) {
        //为 BaseEntity 注册 insertListner
        globalConfig.registerInsertListener(this, BaseEntity.class);

        //为 BaseEntity 注册 updateListener
        globalConfig.registerUpdateListener(this, BaseEntity.class);
    }
}
