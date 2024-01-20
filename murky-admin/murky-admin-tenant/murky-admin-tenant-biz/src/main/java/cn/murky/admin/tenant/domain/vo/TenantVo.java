package cn.murky.admin.tenant.domain.vo;

import cn.murky.admin.common.enums.CommonStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * 租户视图类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public class TenantVo {
    @ApiModelProperty("租户id")
    private Long id;

    @ApiModelProperty("权限组id")
    private Long fkGroupId;

    @ApiModelProperty("权限组名称")
    private String groupName;

    @ApiModelProperty("租户名")
    private String tenantName;

    @ApiModelProperty("租户管理员")
    private Long adminUser;

    @ApiModelProperty("到期时间")
    private OffsetDateTime expires;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("状态")
    private CommonStatus status;

    @ApiModelProperty("创建时间")
    private OffsetDateTime createTime;
}
