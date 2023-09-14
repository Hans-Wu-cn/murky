package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.system.domain.entity.PoemRole;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("角色实体类")
public class PoemRolePageDTO extends Page<PoemRole> {
    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色码")
    private String roleCode;
}
