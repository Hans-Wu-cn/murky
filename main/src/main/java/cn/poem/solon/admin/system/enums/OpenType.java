package cn.poem.solon.admin.system.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.noear.snack.annotation.ONodeAttr;


@ToString
@AllArgsConstructor
@Getter
@JsonFormat
public enum OpenType {
    CURRENT(1,"当前窗口"),
    NEW(2,"新窗口"),
    ;
    @EnumValue
//    @JsonValue
    @ONodeAttr
    private final Integer code;
    private final String des;

}
