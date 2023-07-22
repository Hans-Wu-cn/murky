package cn.poem.solon.admin.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * 菜单类型
 *
 * @author hans
 */
@ToString
@AllArgsConstructor
@Getter
public enum MenuType {
    DIRECTORY(0,"目录"),
    MENU(1,"菜单"),
    BUTTON(2,"按钮"),
    ;
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String des;
}
