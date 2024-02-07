package cn.murky.security.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.solon.dao.SaTokenDaoOfRedis;
import cn.dev33.satoken.solon.integration.SaTokenInterceptor;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Condition;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * SaToken配置类
 *
 * @author hans
 */
@Configuration
public class SaConfig {

    @Bean(index = -100) //-100，是顺序位（低值优先）
    public SaTokenInterceptor saTokenInterceptor(@Inject("${security.path-ignore}") String... ignore) {
        return new SaTokenInterceptor()
                .addInclude("/**")
                .addExclude(ignore);
    }

    @Bean
    @Condition(onMissingBean = SaTokenDao.class)
    public SaTokenDao saTokenDaoInit(@Inject("${redis}") SaTokenDaoOfRedis saTokenDao) {
        return saTokenDao;
    }
}
