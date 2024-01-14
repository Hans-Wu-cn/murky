package cn.murky.admin.tenant.domain.entity;

import cn.murky.admin.common.entity.BaseEntity;
import cn.murky.admin.core.enums.CommonStatus;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
    private ZonedDateTime expires;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("状态")
    private CommonStatus status;
}
