package cn.murky.admin.tenant.api.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.noear.snack.annotation.ONodeAttr;

@AllArgsConstructor
@Getter
public enum EnvTypeEnum {
    REDIS("redis"),
    ;
    @EnumValue
    @ONodeAttr
    private final String code;

    @Override
    public String toString() {
        return this.code;
    }
}
