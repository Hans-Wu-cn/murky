package cn.poem.solon.core.config;

import cn.poem.solon.core.enetity.BaseEntity;
import cn.poem.solon.core.listener.PoemCommonListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.InitializingBean;

/**
 * mybaits-flex配置类
 *
 * @author hans
 */
@Configuration
@Slf4j
public class MyBatisFlexConfiguration implements InitializingBean {

    @Inject("${mybatis.db.auditEnable}")
    boolean auditEnable;


    public void afterInjection() throws Throwable{
        //开启审计功能
        AuditManager.setAuditEnable(auditEnable);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                log.info("{},{}ms", auditMessage.getFullSql(), auditMessage.getElapsedTime())
        );

        //注册mapper监听
        setListener();
    }

    /**
     * 统一注册mapper监听
     */
    public void setListener(){
        PoemCommonListener poemCommonListener = new PoemCommonListener();
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        config.registerInsertListener(poemCommonListener, BaseEntity.class);
        config.registerUpdateListener(poemCommonListener, BaseEntity.class);
    }
}
