package cn.poem.solon.admin.saas.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 是否主键(0:非主键   1:主键)
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum ColumnPrimary {
    NOT_PRIMARY(0,"非主键"),
    PRIMARY(1,"主键"),
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
