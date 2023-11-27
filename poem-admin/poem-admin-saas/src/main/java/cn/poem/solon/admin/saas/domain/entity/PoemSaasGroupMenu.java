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
@Table("poem_saas_group_menu")
public class PoemSaasGroupMenu extends Model<PoemSaasGroupMenu> {
    @ApiModelProperty("角色id")
    private Long groupId;

    @ApiModelProperty("菜单id")
    private Long saasMenuId;

}
