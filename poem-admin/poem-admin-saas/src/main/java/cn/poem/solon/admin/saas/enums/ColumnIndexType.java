package cn.poem.solon.admin.saas.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 0:无索引   1:普通索引   2:唯一索引
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum ColumnIndexType {
    NOT_INDEX(0,"无索引"),
    NORMAL_INDEX(1,"普通索引"),
    UNIQUE_INDEX(1,"唯一索引"),
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
