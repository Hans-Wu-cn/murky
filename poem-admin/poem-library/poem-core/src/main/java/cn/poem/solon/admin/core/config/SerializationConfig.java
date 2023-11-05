//package cn.poem.solon.core.config;
//
//import com.fasterxml.jackson.annotation.JsonValue;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import org.noear.solon.annotation.Bean;
//import org.noear.solon.annotation.Configuration;
//import org.noear.solon.annotation.Inject;
//
//import org.noear.solon.serialization.snack3.SnackActionExecutor;
//import org.noear.solon.serialization.snack3.SnackRenderFactory;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//
///**
// * 序列化配置
// *
// * @author hans
// */
//@Configuration
//public class SerializationConfig {
//    @Bean
//    public void jsonInit(@Inject SnackRenderFactory factory, @Inject SnackActionExecutor executor) {
////        //方式1：通过转换器，做简单类型的定制
////        factory.addConvertor(Date.class, s -> s.getTime());
////
////        factory.addConvertor(LocalDate.class, s -> s.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
////
////        factory.addConvertor(Long.class, s -> String.valueOf(s));
//
//        //方式2：通过编码器，做复杂类型的原生定制（基于框架原生接口）
////        factory.addEncoder(Enum.class, new JsonSerializer<Enum>() {
////            @Override
////            public void serialize(Enum anEnum, JsonGenerator out, SerializerProvider serializerProvider) throws IOException {
////                try {
////                    for (Field field : anEnum.getClass().getDeclaredFields()) {
////                        if (!field.isAnnotationPresent(JsonValue.class)) {
////                            continue;
////                        }
////                        field.setAccessible(true);
////                        if (field.getGenericType() == Integer.class) {
////                            out.writeNumber((Integer) field.get(anEnum));
////                        }
////                        if (field.getGenericType() == String.class) {
////                            out.writeString((String) field.get(anEnum));
////                        }
////                    }
////                } catch (IllegalAccessException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////        });87
//
//        //executor.config()...
//    }
//}
