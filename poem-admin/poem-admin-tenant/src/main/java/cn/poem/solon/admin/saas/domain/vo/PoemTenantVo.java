package cn.poem.solon.admin.saas.domain.vo;

import cn.poem.solon.admin.core.enums.CommonStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 租户视图类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public class PoemTenantVo {
    @ApiModelProperty("租户id")
    private Long tenantId;

    @ApiModelProperty("权限组id")
    private Long groupId;

    @ApiModelProperty("权限组名称")
    private String groupName;

    @ApiModelProperty("租户名")
    private String tenantName;

    @ApiModelProperty("租户管理员")
    private Long adminUser;

    @ApiModelProperty("到期时间")
    private LocalDateTime expires;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("状态")
    private CommonStatus status;
}
