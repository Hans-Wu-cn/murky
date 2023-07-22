package cn.poem.mybatisplus.config;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

/**
 * 配置数据源
 *
 * @author hans
 */
@Configuration
public class MybatisPlusConfig {
        //此下的 db1 与 mybatis.db1 将对应在起来 //可以用 @Db("db1") 注入mapper
        //typed=true，表示默认数据源。@Db 可不带名字注入
        @Bean(value = "db1", typed = true)
        public DataSource db1(@Inject("${postgres.db}") HikariDataSource ds) {
            return ds;
        }

        //调整 db1 的配置（如：增加插件）// (配置可以解决的，不需要这块代码)
        //@Bean
        //public void db1_cfg(@Db("db1") MybatisConfiguration cfg,
        //                    @Db("db1") GlobalConfig globalConfig) {

        //    cfg.setCacheEnabled(false);

        //    //globalConfig.setIdentifierGenerator(null);
        //}
}
