package cn.poem.solon.admin.saas.domain.entity;

import cn.poem.solon.admin.common.entity.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商户角色实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("商户角色实体类")
@Table(value = "poem_saas_role")
public class PoemSaasRole extends BaseEntity {
    @Id
    @ApiModelProperty("主键")
    private Long saasRoleId;

    @ApiModelProperty("角色名")
    private String saasRoleName;

    @ApiModelProperty("角色码")
    private String saasRoleCode;

    @ApiModelProperty("描述")
    private String describe;
}
