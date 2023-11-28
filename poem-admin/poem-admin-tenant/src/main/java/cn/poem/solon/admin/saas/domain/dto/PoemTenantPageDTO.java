package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.saas.domain.entity.PoemTenant;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("租户分页DTO实体类")
public class PoemTenantPageDTO  extends Page<PoemTenant> {

    @ApiModelProperty("租户名称")
    private String tenantName;
}
