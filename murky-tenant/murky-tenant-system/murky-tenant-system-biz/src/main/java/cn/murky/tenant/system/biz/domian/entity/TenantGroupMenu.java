package cn.murky.tenant.system.biz.domian.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/***
 * 租户权限码菜单关系实体
 *
 * @author hans
 */
@Data(staticConstructor = "create")
@Accessors(chain = true)
@ApiModel("租户权限码菜单关系实体类")
@Table("tenant_group_menu")
public class TenantGroupMenu extends Model<TenantGroupMenu> {
    @ApiModelProperty("权限组id")
    private Long fkGroupId;

    @ApiModelProperty("菜单id")
    private Long fkMenuId;

}
