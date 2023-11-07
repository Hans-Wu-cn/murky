package cn.poem.solon.admin.event.system.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 字典状态（0正常 1停用）
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum DictStatus {
    NORMAL(0,"正常"),
    STOP(1,"停用")
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
