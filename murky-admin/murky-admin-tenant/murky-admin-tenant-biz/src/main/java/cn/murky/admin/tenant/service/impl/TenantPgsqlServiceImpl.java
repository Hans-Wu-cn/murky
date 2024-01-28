package cn.murky.admin.tenant.service.impl;

import cn.murky.admin.tenant.service.ITenantDDLService;
import com.mybatisflex.core.row.Db;
import org.noear.solon.annotation.Component;
import org.noear.solon.data.annotation.Tran;

import java.util.Optional;

/**
 * pgsql租户初始化实现
 */
@Component
@Tran
public class TenantPgsqlServiceImpl implements ITenantDDLService {
    @Override
    public void createSchema(String schemaName) {
        assertSchema(schemaName);
        Db.updateBySql(STR."CREATE SCHEMA \{schemaName}");
    }

    @Override
    public void createTable(String schemaName) {
        assertSchema(schemaName);
        // 租户系统基础权限表
        createRoleTable(schemaName);
        createRoleMenuTable(schemaName);
        createUserTable(schemaName);
        createRoleDeptTable(schemaName);
    }

    @Override
    public void initData(String schemaName) {
        assertSchema(schemaName);

    }

    /**
     * 创建角色表
     *
     * @param schemaName 模式名称
     */
    public void createRoleTable(String schemaName) {
        String tableName="tenant_role";
        // 创建租户角色表
        Db.updateBySql(STR."""
                CREATE TABLE \{schemaName}.\{tableName} (
                	create_time timestamp NULL,
                	update_time timestamp NULL,
                	create_user int8 NULL,
                	update_user int8 NULL,
                	id int8 NOT NULL,
                	role_name varchar NOT NULL,
                	"describe" varchar NULL,
                	role_code varchar NOT NULL,
                	data_scope int2 NOT NULL DEFAULT 0,
                	fk_dept_id int8 NULL,
                	CONSTRAINT tenant_role_pk PRIMARY KEY (id)
                );
                """);
        commonColumComment(schemaName,tableName);
        columComment(schemaName,tableName,"role_name","角色名");
        columComment(schemaName,tableName,"describe","描述");
        columComment(schemaName,tableName,"role_code","角色码");
        columComment(schemaName,tableName,"data_scope","数据范围");
        columComment(schemaName,tableName,"fk_dept_id","部门id");
    }

    /**
     * 创建角色菜单关系表
     *
     * @param schemaName 模式名称
     */
    public void createRoleMenuTable(String schemaName) {
        String tableName="tenant_role_menu";
        // 创建租户角色权限关系表
        Db.updateBySql(STR."""
                CREATE TABLE \{schemaName}.\{tableName} (
                	fk_menu_id int8 NOT NULL,
                	fk_role_id int8 NOT NULL,
                	CONSTRAINT sys_role_menu_pk PRIMARY KEY (fk_menu_id, fk_role_id)
                );
                """);
        columComment(schemaName,tableName,"fk_menu_id","菜单id");
        columComment(schemaName,tableName,"fk_role_id","角色id");
    }

    /**
     * 创建租户用户表
     *
     * @param schemaName 模式名称
     */
    public void createUserTable(String schemaName) {
        String tableName="tenant_user";
        // 创建租户角色权限关系表
        Db.updateBySql(STR."""
                CREATE TABLE \{schemaName}.\{tableName} (
                	create_time timestamp NULL,
                	update_time timestamp NULL,
                	create_user int8 NULL,
                	update_user int8 NULL,
                	id int8 NOT NULL,
                	fk_role_id int8 NOT NULL,
                	fk_dept_id int8 NULL,
                	user_name varchar NOT NULL,
                	account varchar NOT NULL,
                	"password" varchar NOT NULL,
                	sex int2 NOT NULL,
                	email varchar NULL,
                	"language" varchar NULL,
                	salt varchar NULL,
                	CONSTRAINT tenant_user_pk PRIMARY KEY (id)
                );
                """);
        commonColumComment(schemaName,tableName);
        columComment(schemaName,tableName,"fk_role_id","角色id");
        columComment(schemaName,tableName,"fk_dept_id","部门id");
        columComment(schemaName,tableName,"user_name","用户名称");
        columComment(schemaName,tableName,"account","账号");
        columComment(schemaName,tableName,"password","密码");
        columComment(schemaName,tableName,"sex","性别");
        columComment(schemaName,tableName,"email","邮箱");
        columComment(schemaName,tableName,"language","语言");
        columComment(schemaName,tableName,"salt","密码盐值");
    }

    /**
     * 创建租户用户角色关系表
     *
     * @param schemaName 模式名称
     */
    public void createRoleDeptTable(String schemaName) {
        String tableName="tenant_role_dept";
        // 创建租户角色权限关系表
        Db.updateBySql(STR."""
                CREATE TABLE \{schemaName}.\{tableName} (
                	fk_role_id int8 NOT NULL,
                	fk_dept_id int8 NOT NULL,
                	CONSTRAINT sys_role_dept_pk PRIMARY KEY (fk_role_id,fk_dept_id)
                );
                """);
        columComment(schemaName,tableName,"fk_role_id","角色id");
        columComment(schemaName,tableName,"fk_dept_id","部门id");
    }

    private void assertSchema(String schemaName) {
        Optional.ofNullable(schemaName).orElseThrow();
    }

    private void commonColumComment(String schemaName,String tableName){
        columComment(schemaName,tableName,"create_time","创建时间");
        columComment(schemaName,tableName,"update_time","修改时间");
        columComment(schemaName,tableName,"create_user","创建人");
        columComment(schemaName,tableName,"update_user","修改人");
    }

    private void columComment(String schemaName, String tableName, String columName, String comment){
        Db.updateBySql(STR."""
               COMMENT ON COLUMN \{schemaName}.\{tableName}.\{columName} IS '\{comment}';
                """);
    }
}
