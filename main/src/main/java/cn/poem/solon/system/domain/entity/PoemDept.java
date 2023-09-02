package cn.poem.solon.system.domain.entity;

import cn.poem.solon.mybatisflex.extension.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table("poem_dept")
@ApiModel
public class PoemDept extends BaseEntity {

    @Id
    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("父级部门id")
    private Long parentDept;

    @ApiModelProperty("祖级部门")
    private String ancestors;

    @ApiModelProperty("排序")
    private Integer sort;
}
