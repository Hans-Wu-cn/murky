package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasPermissionGroupConvert;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasPermissionGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.util.List;

/**
 * 商户权限组表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("商户权限组表单类")
public class PoemSaasPermissionGroupFromDTO {
    @ApiModelProperty("权限组id")
    @NotNull(groups = Update.class)
    private Long groupId;

    @ApiModelProperty(value = "权限组名", required = true)
    @NotBlank
    private String groupName;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("所属菜单id")
    private List<Long> saasMenuIds;

    public PoemSaasPermissionGroup toEntity() {
        return PoemSaasPermissionGroupConvert.INSTANCES.toEntity(this);
    }
}
