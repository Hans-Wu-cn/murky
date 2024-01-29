package cn.murky.tenant.system.api.domain.bo;

import cn.murky.common.enums.DataScope;
import lombok.Data;

@Data
public class SysRoleBO {

    /**
     * ID
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 部门id
     */
    private Long fkDeptId;

    /**
     * 所属部门的数据权限
     */
    private DataScope dataScope;

    /**
     * 描述
     */
    private String describe;
}
