package cn.murky.admin.core.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 通用状态（0正常 1停用）
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum CommonStatus {
    NORMAL(0, "正常"),
    STOP(1, "停用");

    @EnumValue
    @ONodeAttr
    private final Integer code;
    private final String des;

    public CommonStatus cut() {
        switch (this){
            case NORMAL -> {
                return STOP;
            }
            case STOP -> {
                return NORMAL;
            }
        }
        return NORMAL;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.des;
    }
}
