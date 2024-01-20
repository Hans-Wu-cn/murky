package cn.murky.admin.system.biz.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * 部门树视图
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单树视图类")
public class SysDeptTreeVO {
    @ApiModelProperty("部门id")
    private Long id;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("上级部门id")
    private Long parentId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建事件")
    private OffsetDateTime createTime;

    @ApiModelProperty("子部门")
    private List<SysDeptTreeVO> children;
}
