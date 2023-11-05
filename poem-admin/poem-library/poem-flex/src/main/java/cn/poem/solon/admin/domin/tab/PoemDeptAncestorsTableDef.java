package cn.poem.solon.admin.domin.tab;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class PoemDeptAncestorsTableDef extends TableDef {

    /**
     * 部门祖级关系
     */
    public static final PoemDeptAncestorsTableDef POEM_DEPT_ANCESTORS = new PoemDeptAncestorsTableDef();

    /**
     * 部门id
     */
    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");

    /**
     * 祖级部门Id
     */
    public final QueryColumn ANCESTORS = new QueryColumn(this, "ancestors");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{DEPT_ID, ANCESTORS};

    public PoemDeptAncestorsTableDef() {
        super("", "poem_dept_ancestors");
    }

}
