package cn.poem.solon.admin.system.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 是否开启缓存 0:关闭  1:开启
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum MenuCacheType {
    ON(0,"开启缓存"),
    OFF(1,"关闭缓存"),
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
