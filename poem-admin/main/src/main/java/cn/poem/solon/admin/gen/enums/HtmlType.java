package cn.poem.solon.admin.gen.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

/**
 * 显示类型（0:文本框、1:文本域、2:下拉框、3:复选框、4:单选框、5:日期控件 6:图片上传控件 7:富文本）
 *
 * @author hans
 */
@AllArgsConstructor
@Getter
public enum HtmlType {
    INPUT(0, "文本框"),
    TEXTAREA(1, "文本域"),
    SELECT(2, "下拉框"),
    CHECKBOX(3, "复选框"),
    RADIO(4, "单选框"),
    DATE(5, "日期控件"),
    IMAGE_UPLOAD(6, "图片上传控件"),
    EDITOR(7, "富文本"),
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
