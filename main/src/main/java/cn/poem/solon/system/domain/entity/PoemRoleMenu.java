package cn.poem.solon.system.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
@Table("poem_role_menu")
public class PoemRoleMenu {
//    @Id(keyType = KeyType.None)
    @ApiModelProperty("角色Id")
    private Long roleId;

//    @Id(keyType = KeyType.None)
    @ApiModelProperty("菜单Id")
    private Long menuId;

}
