package cn.poem.solon.admin.gen.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 0:否  1:是
 */
@AllArgsConstructor
@Getter
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是"),
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
