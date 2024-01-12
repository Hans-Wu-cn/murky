package cn.murky.core.config;

import org.noear.snack.core.Feature;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.serialization.snack3.SnackActionExecutor;
import org.noear.solon.serialization.snack3.SnackRenderFactory;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class SnackConfig {
    @Bean
    public void jsonInit(@Inject SnackRenderFactory factory, @Inject SnackActionExecutor executor){
//
//        executor.config().addDecoder(ZonedDateTime.class, data->{
//            return null;
//        });

    }
}
