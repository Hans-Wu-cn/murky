package cn.poem.solon.admin.system.domain.entity;

import cn.poem.solon.admin.common.entity.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Table("sys_dept")
@Accessors(chain = true)
@ApiModel
public class SysDept extends BaseEntity {

    @Id
    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("父级部门id")
    private Long parentDept;

    @ApiModelProperty("排序")
    private Integer sort;
}
