package cn.poem.solon.admin.system.api.enums;


import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;


@AllArgsConstructor
@Getter
public enum MenuOpenType {
    CURRENT(1,"当前窗口"),
    NEW(2,"新窗口"),
    ;
    @EnumValue
    @ONodeAttr
    private final Integer code;
    private final String des;

    @Override
    public String toString() {
        return this.code+":"+this.des;
    }
}
