package cn.poem.solon.admin.gen.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 查询方式（0:等于、1:不等于、2:大于、3:小于、4:范围）
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum QueryType {
    EQ(0, "等于"),
    NE(1, "不等于"),
    GT(2, "大于"),
    LT(3, "小于"),
    RANGE(4, "范围"),
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
