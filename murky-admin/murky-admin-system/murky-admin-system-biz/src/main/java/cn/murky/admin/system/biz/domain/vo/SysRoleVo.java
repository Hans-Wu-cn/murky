package cn.murky.admin.system.biz.domain.vo;

import cn.murky.admin.common.enums.DataScope;
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
public class SysRoleVo implements Serializable {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("数据权限")
    private DataScope dataScope;

    @ApiModelProperty("菜单id集合")
    private List<Long> fkMenuIds;

    @ApiModelProperty("部门Id集合")
    private List<Long> fkDeptIds;
}
