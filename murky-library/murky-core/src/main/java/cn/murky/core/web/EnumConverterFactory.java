package cn.murky.core.web;

import org.noear.snack.core.exts.EnumWrap;
import org.noear.snack.core.utils.TypeUtil;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.convert.Converter;
import org.noear.solon.core.convert.ConverterFactory;
import org.noear.solon.core.exception.ConvertException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class EnumConverterFactory implements ConverterFactory<String, Enum> {
    private static final Map<Class, Converter> CONVERTERS = new HashMap<>();

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter converter = CONVERTERS.get(targetType);
        if (converter == null) {
            converter = new EnumTypeConverter<>(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return converter;
    }

    public static class EnumTypeConverter<T extends Enum> implements Converter<String, T> {
        private final EnumWrap enumWrap;

        public EnumTypeConverter(Class<T> targetType) {
            enumWrap = TypeUtil.createEnum(targetType);
        }

        @Override
        public T convert(String value) throws ConvertException {
            Enum wrapCustom = enumWrap.getCustom(value);
            if (wrapCustom != null) {
                return (T) wrapCustom;
            }
            return (T) enumWrap.get(value);
        }
    }

    public static void main(String []args) throws Exception {


        String url = "jdbc:postgresql://119.29.203.51:5432/poem-solon?currentSchema=admin";

        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "123456");
        try ( Connection conn = DriverManager.getConnection(url, props) ){
            try ( Statement statement = conn.createStatement() ) {
                try (ResultSet rs = statement.executeQuery( "select * from tenant order by expires asc limit 1") ){
                    if (rs.next())
                        System.out.println( "Get String: " + rs.getLong("id"));
                    System.out.println( "Get String: " + rs.getObject("expires", OffsetDateTime.class));
                }
            }
        }
    }
}
