package cn.poem.solon.admin.gen.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 生成代码方式（0zip压缩包 1自定义路径）
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum GenType {
    ZIP(0, "zip压缩包"),
    PATH(1, "自定义路径"),
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
