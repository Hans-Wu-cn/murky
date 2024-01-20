package cn.murky.admin.tenant.domain.entity;

import cn.murky.common.entity.BaseEntity;
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
@Table(value = "tenant_permission_group")
public class TenantPermissionGroup extends BaseEntity<TenantPermissionGroup> {
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("权限组名")
    private String groupName;

    @ApiModelProperty("描述")
    private String describe;
}
