package cn.murky.tenant.system.api.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 该菜单是否使用外链内嵌进系统
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum MenuOutside {
    ON(0,"不使用外链"),
    YES(1,"使用外链"),
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

