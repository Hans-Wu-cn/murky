package cn.murky.tenant.system.api.domain.bo;

import cn.murky.common.enums.DataScope;
import lombok.Data;

@Data
public class TenantRoleBO {
    /**
     * ID
     */
    private Long id;

    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 是否是租户管理员
     */
    private Boolean admin;

    /**
     * 所属部门的数据权限
     */
    private DataScope dataScope;
}
