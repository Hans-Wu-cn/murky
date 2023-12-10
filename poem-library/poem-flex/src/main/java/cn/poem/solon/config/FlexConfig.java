package cn.poem.solon.config;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;


/**
 * mybatis-flex配置类
 *
 * @author hans
 */
@Configuration
public class FlexConfig {
    //typed=true，表示默认数据源。@Db 可不带名字注入
    @Bean(value = "db", typed = true)
    public DataSource db(@Inject("${postgres.db}") HikariDataSource ds) {
        return ds;
    }

}
