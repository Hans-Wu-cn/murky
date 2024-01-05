package cn.murky.admin.system.biz.domain.entity;

import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/***
 * 角色菜单关系实体
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色菜单关系实体类")
@Table("sys_role_menu")
public class SysRoleMenu {
    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("菜单id")
    private Long menuId;

}
