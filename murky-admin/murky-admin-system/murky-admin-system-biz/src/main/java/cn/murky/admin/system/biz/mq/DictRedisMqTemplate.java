package cn.murky.admin.system.biz.mq;

import cn.murky.admin.system.biz.endpoint.AdminDataEndpoint;
import cn.murky.redismq.RedisMqTemplate;
import lombok.extern.slf4j.Slf4j;
import org.noear.redisx.RedisClient;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.io.IOException;

@Component
@Slf4j
public class DictRedisMqTemplate implements RedisMqTemplate {
    @Inject
    private RedisClient redisClient;
    @Inject
    private AdminDataEndpoint adminDataEndpoint;
    @Override
    public void publish(ONode oNode) {
        log.info("[DictRedisMqTemplate] -> publish 发布消息 topic:{}",getTopic());
        redisClient.getBus().publish(getTopic(),oNode.toJson());
    }

    public void publish() {
        log.info("[DictRedisMqTemplate] -> publish 发布消息 topic:{}",getTopic());
        redisClient.getBus().publish(getTopic(),"");
    }

        @Override
        public void subscribe() {
            log.info("[DictRedisMqTemplate] -> 订阅主题 -> topic:{}",getTopic());
            redisClient.getBus().subscribe((topic, msg) -> {
            log.info("[DictRedisMqTemplate] -> 消费消息 -> topic:{}",getTopic());
            try {
                adminDataEndpoint.publishOnDict();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        },getTopic());
    }

    @Override
    public String getTopic() {
        return "topic:dict:data";
    }
}
