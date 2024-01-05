package cn.murky.admin.system.api.domian.dto;


import cn.murky.admin.flex.enums.Sex;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotEmpty;
import org.noear.solon.validation.annotation.NotNull;

@Data
@Accessors(chain = true)
public class ProfileFromDTO {

    /**
     * 用户名
     */
    @NotEmpty
    private String userName;

    /**
     * 邮箱
     */
    @NotEmpty
    private String email;

    /**
     * 性别
     */
    @NotNull
    private Sex sex;
}
