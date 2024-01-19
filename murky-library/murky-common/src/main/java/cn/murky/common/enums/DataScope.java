package cn.murky.common.enums;


import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 数据范围(0:全部数据权限 1:自定数据权限 2:本部门及以下数据权限 3:本部门数据权限 4:仅本人 )
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum DataScope {
    ALL(0,"全部数据权限"),
    CUSTOMIZE(1,"自定数据权限"),
    DEPARTMENT_BELOW(2,"本部门及以下数据权限"),
    DEPARTMENT(3,"本部门数据权限"),
    ONESELF(4,"仅本人"),
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
