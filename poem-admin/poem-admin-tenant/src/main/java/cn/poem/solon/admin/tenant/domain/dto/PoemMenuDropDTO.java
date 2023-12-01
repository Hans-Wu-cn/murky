package cn.poem.solon.admin.tenant.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotEmpty;

import java.util.List;

/**
 * 租户菜单拖动接口参数实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("租户菜单拖动接口参数实体类")
public class PoemMenuDropDTO {

    @ApiModelProperty("租户父级菜单id")
    private Long parentSaasMenuId;

    @ApiModelProperty(value = "菜单id集合,按顺序排列", required = true)
    @NotEmpty
    private List<Long> saasMenuIds;
}
