package cn.murky.redismq.config;


import cn.murky.redismq.RedisMqTemplate;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.util.List;

@Configuration
public class RedisMqConfig {


    @Bean
    public void redisMqTemplate(@Inject List<RedisMqTemplate> redisMqTemplateList){
        Thread.startVirtualThread(()->{
            redisMqTemplateList.forEach(RedisMqTemplate::subscribe);
        });

    }

}
