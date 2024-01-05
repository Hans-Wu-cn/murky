package cn.murky.admin.tenant.domain.dto;

import cn.murky.core.validat.Insert;
import cn.murky.core.validat.Update;
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
public class TenantFromDTO {
    @ApiModelProperty("租户id")
    @NotNull(groups = Update.class)
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

    @ApiModelProperty("账号")
    @NotBlank(groups = Insert.class)
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(groups = Insert.class)
    private String password;

    @ApiModelProperty("二次确认密码")
    @NotBlank(groups = Insert.class)
    private String confirmPassword;
}
