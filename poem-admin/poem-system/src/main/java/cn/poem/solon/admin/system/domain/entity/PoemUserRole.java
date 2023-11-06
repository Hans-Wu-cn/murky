package cn.poem.solon.admin.system.domain.entity;

import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户角色关系实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("用户角色关系实体类")
@Table("poem_user_role")
public class PoemUserRole {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("角色id")
    private Long roleId;
}
