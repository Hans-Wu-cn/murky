package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.saas.domain.entity.PoemTenantPermissionGroup;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("商户权限组分页DTO实体类")
public class PoemTenantPermissionGroupPageDTO extends Page<PoemTenantPermissionGroup> {
    @ApiModelProperty("权限组名")
    private String groupName;

}
