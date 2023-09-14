package cn.poem.solon.admin.domin;

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
@Table("poem_role_dept")
public class PoemRoleDept extends Model<PoemRoleDept> {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 部门id
     */
    private Long deptId;

}
