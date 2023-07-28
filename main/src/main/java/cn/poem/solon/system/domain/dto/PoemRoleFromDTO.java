package cn.poem.solon.system.domain.dto;

import cn.poem.core.validat.Update;
import cn.poem.solon.system.domain.convert.PoemMenuConvert;
import cn.poem.solon.system.domain.convert.PoemRoleConvert;
import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.domain.entity.PoemRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotNull;

/**
 * 角色表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色表单类")
public class PoemRoleFromDTO {
    @ApiModelProperty("角色id")
    @NotNull(groups = Update.class)
    private Long roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String describe;

    public PoemRole toEntity(){
        return PoemRoleConvert.INSTANCES.toEntity(this);
    }
}
