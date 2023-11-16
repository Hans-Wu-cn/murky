package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("商户角色分页DTO实体类")
public class PoemSaasRolePageDTO extends Page<PoemSaasRole> {
    @ApiModelProperty("角色名")
    private String saasRoleName;

    @ApiModelProperty("角色码")
    private String saasRoleCode;
}
