package cn.poem.solon.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@ApiModel
public class LoginDto {
    @ApiModelProperty("账号")
    @NotBlank
    private String account;

    @ApiModelProperty("密码")
    @NotBlank
    private String password;
}
