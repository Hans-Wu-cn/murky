package cn.murky.admin.system.biz.domain.dto;

import cn.murky.common.enums.DataScope;
import cn.murky.admin.system.biz.convert.SysRoleConvert;
import cn.murky.admin.system.biz.domain.entity.SysRole;
import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.util.List;

/**
 * 角色表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色表单类")
public class SysRoleFromDTO {
    @ApiModelProperty("角色id")
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank
    private String roleName;

    @ApiModelProperty(value = "角色码", required = true)
    @NotBlank
    private String roleCode;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty(value = "数据权限",required = true)
    @NotNull
    private DataScope dataScope;

    @ApiModelProperty("所属菜单id")
    private List<Long> fkMenuIds;

    @ApiModelProperty("自定义权限部门Id")
    private List<Long> fkDeptIds;

    public SysRole toEntity() {
        return SysRoleConvert.INSTANCES.toEntity(this);
    }
}
