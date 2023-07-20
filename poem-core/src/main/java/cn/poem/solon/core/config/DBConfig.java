package cn.poem.solon.core.config;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Bean(value = "db",typed = true)
    public DataSource db(@Inject("${postgres.db}")HikariDataSource ds){
        return ds;
    }

//    @Bean
//    public void db1_cfg(@Db("db") MybatisConfiguration cfg,
//                        @Db("db") GlobalConfig globalConfig) {
//        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
//        plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MARIADB));
//
//        cfg.setCacheEnabled(false);
//        cfg.addInterceptor(plusInterceptor);
//
////        globalConfig.setSqlInjector(new MyLogicSqlInjector());
//    }
}
