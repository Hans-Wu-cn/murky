package cn.poem.solon.admin.system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@ToString
@AllArgsConstructor
@Getter
public enum OpenType {
    CURRENT(1,"当前窗口"),
    NEW(2,"新窗口"),
    ;
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String des;

}
