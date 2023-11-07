package cn.poem.solon.admin.system.domain.vo;

import cn.poem.solon.admin.common.enums.DataScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 角色视图类,包含对应的菜单关系
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色视图类")
public class PoemRoleVo implements Serializable {
    @ApiModelProperty("主键")
    private Long roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("数据权限")
    private DataScope dataScope;

    @ApiModelProperty("菜单id集合")
    private List<Long> menuIds;

    @ApiModelProperty("部门Id集合")
    private List<Long> deptIds;
}
