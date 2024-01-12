package cn.murky.admin.system.biz.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotEmpty;

import java.util.List;


/**
 * 部门拖动排序接口参数实体类
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("部门拖动排序接口参数实体类")
public class SysDeptDropDTO {
    @ApiModelProperty("父级部门id")
    private Long parentId;

    @ApiModelProperty(value = "部门id集合,按顺序排列", required = true)
    @NotEmpty
    private List<Long> deptIds;
}
