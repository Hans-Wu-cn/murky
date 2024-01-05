package cn.murky.admin.flex.domin.tab;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class SysUserTableDef extends TableDef {

    /**
     * 用户实体类
     */
    public static final SysUserTableDef SYS_USER = new SysUserTableDef();

    /**
     * 性别 0:男性 1:女性 2:其他
     */
    public final QueryColumn SEX = new QueryColumn(this, "sex");

    /**
     * 邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 邮箱
     */
    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 账号
     */
    public final QueryColumn ACCOUNT = new QueryColumn(this, "account");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 用户名
     */
    public final QueryColumn USER_NAME = new QueryColumn(this, "user_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 修改人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SEX, EMAIL, DEPT_ID, USER_ID, ACCOUNT, PASSWORD, USER_NAME, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public SysUserTableDef() {
        super("", "sys_user");
    }

}
