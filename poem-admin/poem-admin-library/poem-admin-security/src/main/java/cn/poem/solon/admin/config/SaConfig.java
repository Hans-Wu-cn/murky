package cn.poem.solon.admin.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.solon.dao.SaTokenDaoOfRedis;
import cn.dev33.satoken.solon.integration.SaTokenInterceptor;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

/**
 * SaToken配置类
 * @author hans
 */
@Configuration
public class SaConfig {
    @Bean(index = -100) //-100，是顺序位（低值优先）
    public SaTokenInterceptor saTokenInterceptor() {
        return new SaTokenInterceptor()
                .addInclude("/**")
                .addExclude("/swagger-resources")
                .addExclude("/auth/login","/auth/logout","/poemI18n/language");
    }

    @Bean
    public SaTokenDao saTokenDaoInit(@Inject("${redis}") SaTokenDaoOfRedis saTokenDao) {
        return saTokenDao;
    }
}
