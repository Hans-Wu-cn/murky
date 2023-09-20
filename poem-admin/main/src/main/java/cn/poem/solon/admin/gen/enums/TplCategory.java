package cn.poem.solon.admin.gen.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 使用的模板（0单表操作1树表操作 2主子表操作）
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum TplCategory {
    SINGLE(0, "单表操作"),
    TREE(1, "树表操作"),
    ;
    @EnumValue
    @ONodeAttr
    private final Integer code;
    private final String des;

    @Override
    public String toString() {
        return this.code + ":" + this.des;
    }
}
