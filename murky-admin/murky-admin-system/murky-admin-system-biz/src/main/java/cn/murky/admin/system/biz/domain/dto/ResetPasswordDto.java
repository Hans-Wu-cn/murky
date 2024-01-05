package cn.murky.admin.system.biz.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("用户密码重置类")
public class ResetPasswordDto {
    @ApiModelProperty(value = "用户id",required = true)
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank
    private String password;

    @ApiModelProperty(value = "二次确定密码",required = true)
    @NotBlank
    private String confirmPassword;
}
