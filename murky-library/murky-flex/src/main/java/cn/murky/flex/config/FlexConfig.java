package cn.murky.flex.config;

import cn.murky.flex.FlexListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.solon.annotation.Db;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Condition;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;
import java.util.List;


/**
 * mybatis-flex配置类
 *
 * @author hans
 */
@Configuration
public class FlexConfig {


    //typed=true，表示默认数据源。@Db 可不带名字注入
    @Condition(onClass=DataSource.class)
    @Bean(value = "db", typed = true)
    public DataSource db(@Inject("${postgres.db}") HikariDataSource ds) {
        return ds;
    }

    @Bean
    public void dbCfg(@Db FlexConfiguration cfg,
                      @Db FlexGlobalConfig globalConfig,
                      @Inject List<FlexListener> flexListeners
    ) {
        //1.初始化数据填充器
        flexListeners.forEach(item->item.registerListener(globalConfig));
    }

}
