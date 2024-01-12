package cn.murky.admin.tenant.domain.entity;

import cn.murky.admin.common.entity.BaseEntity;
import cn.murky.admin.flex.enums.Sex;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户用户实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@Table("tenant_user")
@ApiModel("租户用户实体类")
public class TenantUser extends BaseEntity<TenantUser> {

    @Id
    @ApiModelProperty("租户用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别 0:男性 1:女性 2:其他")
    private Sex sex;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("语言")
    private String language;

    @ApiModelProperty("密码加密盐值")
    private String salt;

    @ApiModelProperty("租户id")
    private Long tenantId;

}
