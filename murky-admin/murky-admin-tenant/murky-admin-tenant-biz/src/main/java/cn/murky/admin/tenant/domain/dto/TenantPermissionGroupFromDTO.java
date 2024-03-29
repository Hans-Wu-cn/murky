package cn.murky.admin.tenant.domain.dto;

import cn.murky.admin.tenant.domain.convert.TenantPermissionGroupConvert;
import cn.murky.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.util.List;

/**
 * 租户权限组表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("租户权限组表单类")
public class TenantPermissionGroupFromDTO {
    @ApiModelProperty("权限组id")
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty(value = "权限组名", required = true)
    @NotBlank
    private String groupName;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("所属菜单id")
    private List<Long> tenantMenuIds;

    public TenantPermissionGroup toEntity() {
        return TenantPermissionGroupConvert.INSTANCES.toEntity(this);
    }
}
