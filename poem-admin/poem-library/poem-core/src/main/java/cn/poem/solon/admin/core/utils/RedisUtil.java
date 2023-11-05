package cn.poem.solon.admin.core.utils;

import org.noear.redisx.RedisClient;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Inject;

public class RedisUtil{
    private static RedisClient redisClient;

    static {
        Solon.context().getBeanAsync(RedisClient.class,r ->{
            redisClient=r;
        });
    }

    public static void set(){

    }

}
