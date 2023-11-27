package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.saas.domain.entity.PoemSaasPermissionGroup;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("商户权限组分页DTO实体类")
public class PoemSaasPermissionGroupPageDTO extends Page<PoemSaasPermissionGroup> {
    @ApiModelProperty("权限组名")
    private String groupName;

}
