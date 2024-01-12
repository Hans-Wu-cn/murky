package cn.murky.admin.tenant.service;

/**
 * 租户数据库操作接口
 */
public interface ITenantDDLService {

    /**
     * 创建schema
     * @param schemaName 模式名
     */
    void createSchema(String schemaName);

    /**
     * 创建表格
     */
    void createTable(String schemaName);

    /**
     * 初始化租户数据
     */
    void initData(String schemaName);
}
