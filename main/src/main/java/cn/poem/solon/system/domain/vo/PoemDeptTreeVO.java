package cn.poem.solon.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门树视图
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单树视图类")
public class PoemDeptTreeVO {
    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("上级部门id")
    private Long parentDept;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建事件")
    private LocalDateTime createTime;

    @ApiModelProperty("子部门")
    private List<PoemDeptTreeVO> children;
}
