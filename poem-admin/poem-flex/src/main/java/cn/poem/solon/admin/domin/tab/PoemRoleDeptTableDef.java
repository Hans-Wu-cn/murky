package cn.poem.solon.admin.domin.tab;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class PoemRoleDeptTableDef extends TableDef {

    /**
     * 角色部门关系实体类
     */
    public static final PoemRoleDeptTableDef POEM_ROLE_DEPT = new PoemRoleDeptTableDef();

    /**
     * 部门id
     */
    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");

    /**
     * 角色id
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{DEPT_ID, ROLE_ID};

    public PoemRoleDeptTableDef() {
        super("", "poem_role_dept");
    }

}
