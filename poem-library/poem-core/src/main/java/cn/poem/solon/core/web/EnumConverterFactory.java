package cn.poem.solon.core.web;

import org.noear.snack.core.exts.EnumWrap;
import org.noear.snack.core.utils.TypeUtil;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.convert.Converter;
import org.noear.solon.core.convert.ConverterFactory;
import org.noear.solon.core.exception.ConvertException;

import java.util.HashMap;
import java.util.Map;

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
}
