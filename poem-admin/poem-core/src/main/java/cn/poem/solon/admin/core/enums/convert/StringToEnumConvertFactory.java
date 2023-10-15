package cn.poem.solon.admin.core.enums.convert;

import cn.poem.solon.admin.common.BaseEnum;
import cn.poem.solon.admin.enums.Sex;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.bean.InitializingBean;
import org.noear.solon.core.convert.Converter;
import org.noear.solon.core.exception.ConvertException;

import java.util.HashMap;
import java.util.Map;

public class StringToEnumConvertFactory implements Converter<String, BaseEnum>, InitializingBean {
    private Map<String, BaseEnum> enumMap = new HashMap<>();

    @Override
    public BaseEnum convert(String value) throws ConvertException {
        return null;
    }
}
