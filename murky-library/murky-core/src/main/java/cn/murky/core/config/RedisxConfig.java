package cn.murky.core.config;

import org.noear.redisx.RedisClient;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

@Configuration
public class RedisxConfig {

    @Bean
    public RedisClient redisClient(@Inject("${redis}") RedisClient client) {
        return client;
    }


}
