package cn.murky.admin.tenant.domain.entity;

import cn.murky.admin.tenant.api.enums.EnvTypeEnum;
import cn.murky.common.domain.entity.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("租户环境配置表")
@Table("tenant_env")
public class TenantEnv extends BaseEntity<TenantEnv> {
    @Id
    @ApiModelProperty("租户id")
    private Long id;

    @ApiModelProperty("环境类型")
    private EnvTypeEnum envType;

    @ApiModelProperty("租户id")
    private Long fkTenantId;

    @ApiModelProperty("环境配置")
    private String envConfig;


}
