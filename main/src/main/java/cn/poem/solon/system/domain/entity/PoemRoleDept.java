package cn.poem.solon.system.domain.entity;

import com.mybatisflex.core.FlexConsts;
import com.mybatisflex.core.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 角色部门关系实体类
 * @author hans
 */
@Data(staticConstructor = "create")
@Accessors(chain = true)
@ApiModel("角色部门关系实体类")
public class PoemRoleDept extends Model<PoemRoleDept> {
    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("部门id")
    private Long deptId;

}
