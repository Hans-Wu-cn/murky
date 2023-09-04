package cn.poem.config;

import cn.dev33.satoken.solon.integration.SaTokenInterceptor;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;

/**
 * SaToken配置类
 * @author hans
 */
@Configuration
public class SaConfig {
    @Bean(index = -100) //-100，是顺序位（低值优先）
    public SaTokenInterceptor saTokenInterceptor() {
        return new SaTokenInterceptor(); //用于支持规划处理及注解处理
    }
}
