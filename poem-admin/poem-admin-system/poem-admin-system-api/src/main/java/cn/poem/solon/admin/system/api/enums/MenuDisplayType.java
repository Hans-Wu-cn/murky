package cn.poem.solon.admin.system.api.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 是否显示在菜单  0:显示  1:隐藏
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum MenuDisplayType {
    DISPLAY(0,"显示"),
    HIDDEN(1,"隐藏"),
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
