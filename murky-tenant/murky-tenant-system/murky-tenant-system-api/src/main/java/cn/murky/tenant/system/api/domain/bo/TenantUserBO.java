package cn.murky.tenant.system.api.domain.bo;

import cn.murky.tenant.system.api.enums.Sex;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TenantUserBO {
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 0:男性 1:女性 2:其他
     */
    private Sex sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门id
     */
    private Long fkDeptId;

    /**
     * 角色id
     */
    private Long fkRoleId;

    /**
     * 语言
     */
    private String language;

    /**
     * 密码加密盐值
     */
    private String salt;

    /**
     * 租户id
     */
    private Long fkTenantId;

    /**
     * 是否是租户管理员
     */
    private Boolean admin;
}
