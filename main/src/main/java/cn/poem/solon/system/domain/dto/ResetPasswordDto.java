package cn.poem.solon.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel("用户密码重置类")
public class ResetPasswordDto {
    @ApiModelProperty("用户id")
    @NotBlank
    private Long userId;

    @ApiModelProperty("密码")
    @NotBlank
    private String password;
}
