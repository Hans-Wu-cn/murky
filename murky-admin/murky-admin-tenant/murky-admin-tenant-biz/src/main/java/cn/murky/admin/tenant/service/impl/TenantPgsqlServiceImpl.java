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
        Db.updateBySql(STR. "CREATE SCHEMA \{ schemaName }" );
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
        // 创建租户角色表
        Db.updateBySql(STR. """
                CREATE TABLE \{ schemaName }.tenant_role (
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
                """ );
    }

    /**
     * 创建角色菜单关系表
     *
     * @param schemaName 模式名称
     */
    public void createRoleMenuTable(String schemaName) {
        // 创建租户角色权限关系表
        Db.updateBySql(STR. """
                CREATE TABLE \{ schemaName }.tenant_role_menu (
                	fk_menu_id int8 NOT NULL,
                	fk_role_id int8 NOT NULL,
                	CONSTRAINT sys_role_menu_pk PRIMARY KEY (fk_menu_id, fk_role_id)
                );
                """ );
    }

    /**
     * 创建租户用户表
     *
     * @param schemaName 模式名称
     */
    public void createUserTable(String schemaName) {
        // 创建租户角色权限关系表
        Db.updateBySql(STR. """
                CREATE TABLE \{ schemaName }.tenant_user (
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
                """ );
    }

    /**
     * 创建租户用户角色关系表
     *
     * @param schemaName 模式名称
     */
    public void createRoleDeptTable(String schemaName) {
        // 创建租户角色权限关系表
        Db.updateBySql(STR. """
                CREATE TABLE \{ schemaName }.tenant_role_dept (
                	fk_role_id int8 NOT NULL,
                	fk_dept_id int8 NOT NULL,
                	CONSTRAINT sys_role_dept_pk PRIMARY KEY (fk_role_id,fk_dept_id)
                );
                """ );
    }

    private void assertSchema(String schemaName) {
        Optional.ofNullable(schemaName).orElseThrow();
    }
}
