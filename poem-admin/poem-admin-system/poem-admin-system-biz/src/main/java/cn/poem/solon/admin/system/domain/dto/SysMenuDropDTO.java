package cn.poem.solon.admin.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotEmpty;

import java.util.List;

/**
 * 菜单拖动接口参数实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单拖动接口参数实体类")
public class SysMenuDropDTO {

    @ApiModelProperty("父级菜单id")
    private Long parentMenuId;

    @ApiModelProperty(value = "菜单id集合,按顺序排列", required = true)
    @NotEmpty
    private List<Long> menuIds;
}
