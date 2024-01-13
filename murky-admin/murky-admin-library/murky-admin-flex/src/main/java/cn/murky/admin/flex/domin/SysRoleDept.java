package cn.murky.admin.flex.domin;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 角色部门关系实体类
 * @author hans
 */
@Data(staticConstructor = "create")
@Accessors(chain = true)
@Table("sys_role_dept")
public class SysRoleDept extends Model<SysRoleDept> {
    /**
     * 角色id
     */
    private Long fkRoleId;

    /**
     * 部门id
     */
    private Long fkDeptId;

}
