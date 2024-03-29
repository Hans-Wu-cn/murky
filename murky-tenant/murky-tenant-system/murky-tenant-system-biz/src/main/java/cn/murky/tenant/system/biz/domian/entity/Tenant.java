package cn.murky.tenant.system.biz.domian.entity;

import cn.murky.common.domain.entity.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/***
 * 租户信息表
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("租户信息表")
@Table("tenant")
public class Tenant extends BaseEntity<Tenant> {
    @Id
    @ApiModelProperty("租户id")
    private Long id;

    @ApiModelProperty("权限组id")
    private Long fkGroupId;

    @ApiModelProperty("租户名")
    private String tenantName;

    @ApiModelProperty("租户管理员")
    private Long adminUser;

    @ApiModelProperty("到期时间")
    private OffsetDateTime expires;

    @ApiModelProperty("描述")
    private String describe;

}
