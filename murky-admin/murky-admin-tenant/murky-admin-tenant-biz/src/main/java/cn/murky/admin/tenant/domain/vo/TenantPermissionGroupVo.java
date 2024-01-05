package cn.murky.admin.tenant.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 租户角色视图类,包含对应的菜单关系
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("租户角色视图类")
public class TenantPermissionGroupVo implements Serializable {
    @ApiModelProperty("主键")
    private Long groupId;

    @ApiModelProperty("角色名")
    private String groupName;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("菜单id集合")
    private List<Long> tenantMenuIds;
}
