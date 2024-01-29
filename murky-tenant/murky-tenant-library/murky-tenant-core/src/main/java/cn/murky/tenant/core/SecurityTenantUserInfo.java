package cn.murky.tenant.core;

import cn.murky.common.enums.DataScope;
import cn.murky.security.SecurityUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SecurityTenantUserInfo extends SecurityUser {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录token
     */
    private String token;

    /**
     * 是否是超级管理员
     */
    private Boolean admin;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 语言偏好
     */
    private String language;

    /**
     * 所属部门id
     */
    private Long fkDeptId;

    /**
     * 所属部门的数据权限
     */
    private DataScope dataScope;

    /**
     * 角色ID集合
     */
    private Long fkRoleId;

    /**
     * 角色code
     */
    private String roleCode;

    /**
     * 权限码
     */
    private List<String> permissions;

    /**
     * 租户id
     */
    private Long tenantId;
}
