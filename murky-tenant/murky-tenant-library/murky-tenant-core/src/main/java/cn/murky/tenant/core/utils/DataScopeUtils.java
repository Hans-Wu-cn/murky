package cn.murky.tenant.core.utils;

import cn.murky.common.enums.DataScope;
import cn.murky.tenant.core.SecurityTenantUserInfo;
import com.mybatisflex.core.query.CPI;
import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.Utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.StringTemplate.STR;

/**
 * 数据权限工具类
 * 提供数据权限静态调用方法
 *
 * @author hans
 */
@Slf4j
public class DataScopeUtils {
    public static final String DEPT_ANCESTORS_NAME = "department";
    public static final String DEPT_TABLE_NAME = "sys_dept";

    /**
     * 数据权限方法，将query绑定数据权限sql
     *
     * @param query    query
     * @param userInfo 用户信息
     */
    public static void dataScope(QueryWrapper query, SecurityTenantUserInfo userInfo, String deptIdFrom) {
        if (userInfo.getAdmin()) {
            return;
        }
        DataScope dataScope = userInfo.getDataScope();
        if (Utils.isNotEmpty(deptIdFrom)) {
            deptIdFrom = STR."""
                     "\{deptIdFrom}".""";
        } else {
            deptIdFrom = "";
        }
        StringBuilder sql = new StringBuilder();
        if (DataScope.ALL == dataScope) {
            return;
        }
        String deptColum = "fk_dept_id";
        // 获取表名
        List<QueryTable> tableList = CPI.getQueryTables(query);
        if (Utils.isNotEmpty(tableList)) {
            Set<String> tableNameSet = tableList.stream().map(QueryTable::getName).collect(Collectors.toSet());
            if (Utils.isNotEmpty(tableNameSet) && tableNameSet.contains(DEPT_TABLE_NAME)) {
                deptColum = "id";
            }
        }
        if (DataScope.CUSTOMIZE == dataScope) {
            sql.append(STR."""
                          (\{deptIdFrom}\{deptColum} in (select fk_dept_id from sys_role_dept where fk_role_id = (\{userInfo.getFkRoleId()})))""");
        }
        if (DataScope.DEPARTMENT_BELOW == dataScope) {
            sql.append(STR."""
                         (\{deptIdFrom}\{deptColum} in (select \{DEPT_ANCESTORS_NAME}.fk_dept_id from sys_dept_ancestors as \{DEPT_ANCESTORS_NAME} where \{DEPT_ANCESTORS_NAME}.ancestors = \{userInfo.getFkDeptId()}) or \{deptIdFrom}\{deptColum}=\{userInfo.getFkDeptId()})
                          """);

        }
        if (DataScope.DEPARTMENT == dataScope) {
            sql.append(STR."""
                         (\{deptIdFrom}\{deptColum}=\{userInfo.getFkDeptId().toString()})
                        """);
        }
        if (DataScope.ONESELF == dataScope) {
            sql.append(STR."""
                         (\{deptIdFrom}create_user = \{userInfo.getUserId().toString()})
                        """);
        }

        if (sql.isEmpty()) {
            return;
        }
        query.and(sql.toString());
        log.debug("[DataScopeUtils]->dataScope sql:{}", query.toSQL());
    }

    public static void dataScope(QueryWrapper query, SecurityTenantUserInfo userInfo) {
        dataScope(query, userInfo, null);
    }

    public static QueryWrapper dataScope(QueryWrapper query) {
        dataScope(query, SecurityUtils.getUserInfo());
        return query;
    }

    public static QueryWrapper dataScope(QueryWrapper query, String deptIdFrom) {
        dataScope(query, SecurityUtils.getUserInfo(), deptIdFrom);
        return query;
    }


    private DataScopeUtils() {
    }

}
