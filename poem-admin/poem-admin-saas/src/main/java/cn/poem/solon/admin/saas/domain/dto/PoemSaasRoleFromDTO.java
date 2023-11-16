package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasRoleConvert;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.util.List;

/**
 * 商户角色表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("商户角色表单类")
public class PoemSaasRoleFromDTO {
    @ApiModelProperty("角色id")
    @NotNull(groups = Update.class)
    private Long saasRoleId;

    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank
    private String saasRoleName;

    @ApiModelProperty(value = "角色码", required = true)
    @NotBlank
    private String saasRoleCode;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("所属菜单id")
    private List<Long> saasMenuIds;

    public PoemSaasRole toEntity() {
        return PoemSaasRoleConvert.INSTANCES.toEntity(this);
    }
}
