package cn.poem.solon.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@ApiModel
public class LoginDto {
    @ApiModelProperty(value = "账号", required = true)
    @NotBlank
    private String account;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;
}
