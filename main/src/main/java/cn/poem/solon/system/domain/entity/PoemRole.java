package cn.poem.solon.system.domain.entity;

import cn.poem.solon.mybatisflex.enums.DataScope;
import cn.poem.solon.mybatisflex.extension.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotNull;

/**
 * 角色实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色实体类")
@Table("poem_role")
public class PoemRole extends BaseEntity {
    @Id
    @ApiModelProperty("主键")
    private Long roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色码")
    private String roleCode;

    @ApiModelProperty("数据权限")
    private DataScope dataScope;

    @ApiModelProperty("描述")
    private String describe;
}
