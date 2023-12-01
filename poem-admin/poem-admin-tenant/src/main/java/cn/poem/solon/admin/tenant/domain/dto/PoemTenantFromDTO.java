package cn.poem.solon.admin.tenant.domain.dto;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.time.LocalDateTime;

/**
 * 商户菜单表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("商户菜单表单类")
public class PoemTenantFromDTO {
    @ApiModelProperty("租户id")
    private Long tenantId;

    @ApiModelProperty("权限组id")
    @NotNull
    private Long groupId;

    @ApiModelProperty("租户名")
    @NotBlank
    private String tenantName;

    @ApiModelProperty("到期时间")
    @NotNull
    private LocalDateTime expires;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("状态")
    private CommonStatus status;

    @ApiModelProperty("账号")
    @NotBlank
    private String account;

    @ApiModelProperty("密码")
    @NotBlank
    private String password;

    @ApiModelProperty("二次确认密码")
    @NotBlank
    private String confirmPassword;
}
