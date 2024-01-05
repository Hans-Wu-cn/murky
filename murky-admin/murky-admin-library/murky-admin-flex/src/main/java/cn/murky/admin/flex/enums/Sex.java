package cn.murky.admin.flex.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 性别枚举
 */
@AllArgsConstructor
@Getter
public enum Sex {
    MAN(0,"男性"),
    WOMAN(1,"女性"),
    OTHER(2,"其他"),;

    @EnumValue
    @ONodeAttr
    private final Integer code;
    private final String des;

    @Override
    public String toString() {
        return this.code+":"+this.des;
    }
}
