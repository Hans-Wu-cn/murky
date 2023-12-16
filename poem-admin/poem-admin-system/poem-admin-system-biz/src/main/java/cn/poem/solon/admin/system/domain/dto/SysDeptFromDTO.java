package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.system.domain.convert.SysDeptConvert;
import cn.poem.solon.admin.system.domain.entity.SysDept;
import cn.poem.solon.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

/**
 * 部门表单DTO实体类
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("部门表单DTO实体类")
public class SysDeptFromDTO {
    @ApiModelProperty(value = "部门id")
    @NotNull(groups = Update.class)
    private Long deptId;

    @ApiModelProperty(value = "部门名称",required=true)
    @NotBlank
    private String deptName;

    @ApiModelProperty("父级部门id")
    private Long parentDept;

    @ApiModelProperty("排序")
    private Integer sort;
    public SysDept toEntity() {
        return SysDeptConvert.INSTANCES.toEntity(this);
    }
}
