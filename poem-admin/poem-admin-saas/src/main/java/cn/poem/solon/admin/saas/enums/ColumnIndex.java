package cn.poem.solon.admin.saas.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 是否索引(0:不索引  1:索引)
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum ColumnIndex {
    NOT_INDEX(0,"不索引"),
    INDEX(1,"索引"),
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
