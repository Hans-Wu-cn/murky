package cn.poem.solon.system.domain.dto;

import cn.poem.solon.core.validat.Update;
import cn.poem.solon.system.domain.convert.PoemDeptConvert;
import cn.poem.solon.system.domain.convert.PoemMenuConvert;
import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.entity.PoemMenu;
import com.mybatisflex.annotation.Id;
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
public class PoemDeptFromDTO {
    @ApiModelProperty(value = "部门id")
    @NotNull(groups = Update.class)
    private Long deptId;

    @ApiModelProperty(value = "部门名称",required=true)
    @NotBlank
    private String deptName;

    @ApiModelProperty("父级部门id")
    private Long parentDept;

    @ApiModelProperty("祖级部门")
    private String ancestors;

    public PoemDept toEntity() {
        return PoemDeptConvert.INSTANCES.toEntity(this);
    }
}
