package cn.poem.solon.admin.system.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.noear.snack.annotation.ONodeAttr;


/**
 * 菜单类型
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum MenuType {
    DIRECTORY(0,"目录"),
    MENU(1,"菜单"),
    BUTTON(2,"按钮"),
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
