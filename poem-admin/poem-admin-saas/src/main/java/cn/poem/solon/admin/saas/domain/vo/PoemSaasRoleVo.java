package cn.poem.solon.admin.saas.domain.vo;

import cn.poem.solon.admin.common.enums.DataScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 商户角色视图类,包含对应的菜单关系
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("商户角色视图类")
public class PoemSaasRoleVo implements Serializable {
    @ApiModelProperty("主键")
    private Long saasRoleId;

    @ApiModelProperty("角色名")
    private String saasRoleName;

    @ApiModelProperty("角色码")
    private String saasRoleCode;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("菜单id集合")
    private List<Long> saasMenuIds;
}
