package cn.poem.solon.admin.tenant.domain.entity;

import cn.poem.solon.admin.common.entity.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户权限组实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("租户权限组实体类")
@Table(value = "poem_tenant_permission_group")
public class PoemTenantPermissionGroup extends BaseEntity {
    @Id
    @ApiModelProperty("主键")
    private Long groupId;

    @ApiModelProperty("角色名")
    private String groupName;

    @ApiModelProperty("描述")
    private String describe;
}
