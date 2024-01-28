package cn.murky.tenant.system.biz.domian.entity;


import cn.murky.common.entity.BaseEntity;
import cn.murky.tenant.system.api.enums.Sex;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(value = "tenant_user")
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
    private Long fkDeptId;

    @ApiModelProperty("角色id")
    private Long fkRoleId;

    @ApiModelProperty("语言")
    private String language;

    @ApiModelProperty("密码加密盐值")
    private String salt;

    @ApiModelProperty("租户id")
    private Long fkTenantId;

    @ApiModelProperty("是否是租户管理员")
    private Boolean admin;
}
