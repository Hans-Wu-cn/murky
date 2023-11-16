package cn.poem.solon.admin.saas.domain.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/***
 * 商户角色菜单关系实体
 *
 * @author hans
 */
@Data(staticConstructor = "create")
@Accessors(chain = true)
@ApiModel("商户角色菜单关系实体类")
@Table("poem_saas_role_menu")
public class PoemSaasRoleMenu extends Model<PoemSaasRoleMenu> {
    @ApiModelProperty("角色id")
    private Long saasRoleId;

    @ApiModelProperty("菜单id")
    private Long saasMenuId;

}
