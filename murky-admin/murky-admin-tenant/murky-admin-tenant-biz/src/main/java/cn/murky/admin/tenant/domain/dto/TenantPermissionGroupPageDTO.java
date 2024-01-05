package cn.murky.admin.tenant.domain.dto;

import cn.murky.admin.tenant.domain.entity.TenantPermissionGroup;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("租户权限组分页DTO实体类")
public class TenantPermissionGroupPageDTO extends Page<TenantPermissionGroup> {
    @ApiModelProperty("权限组名")
    private String groupName;

}
