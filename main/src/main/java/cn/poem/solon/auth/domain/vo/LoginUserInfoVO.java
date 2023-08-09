package cn.poem.solon.auth.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
@ApiModel("登录用户信息视图类")
public class LoginUserInfoVO {
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("登录token")
    private String token;

    @ApiModelProperty("权限码")
    private Set<String> permissions;

}
