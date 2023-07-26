package cn.poem.mybatisflex.config;

import cn.poem.mybatisflex.extension.BaseEntity;
import cn.poem.mybatisflex.listener.PoemInsertListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.solon.annotation.Db;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.InitializingBean;

import javax.sql.DataSource;

/**
 * mybatis-flex配置类
 *
 * @author hans
 */
@Configuration
public class FlexConfig{
    //此下的 db1 与 mybatis.db1 将对应在起来 //可以用 @Db("db1") 注入mapper
    //typed=true，表示默认数据源。@Db 可不带名字注入
    @Bean(value = "db", typed = true)
    public DataSource db(@Inject("${postgres.db}") HikariDataSource ds) {
        return ds;
    }

    //调整 db1 的配置（如：增加插件）// (配置可以解决的，不需要这块代码)
    @Bean
    public void db1_cfg(@Db FlexConfiguration cfg,
                        @Db FlexGlobalConfig globalConfig) {

        //1.初始化数据填充器
        initListener(globalConfig);
    }

    /**
     * 初始化字段填充器
     */
    private void initListener(FlexGlobalConfig globalConfig){
        PoemInsertListener poemInsertListener = new PoemInsertListener();

        //为 BaseEntity 注册 insertListner
        globalConfig.registerInsertListener(poemInsertListener, BaseEntity.class);

        //为 BaseEntity 注册 updateListener
        globalConfig.registerUpdateListener(poemInsertListener, BaseEntity.class);
    }
}
