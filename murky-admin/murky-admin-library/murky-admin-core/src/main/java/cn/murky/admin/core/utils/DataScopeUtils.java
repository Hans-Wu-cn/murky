package cn.murky.admin.core.utils;

import cn.murky.common.constant.BusTopicConstant;
import cn.murky.common.enums.DataScope;
import cn.murky.security.entity.SecurityUserInfo;
import cn.murky.security.utils.SecurityUtils;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.noear.dami.Dami;
import org.noear.solon.Utils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 数据权限工具类
 * 提供数据权限静态调用方法
 *
 * @author hans
 */
@Slf4j
public class DataScopeUtils {
    public static final String DEPT_TABLE_NAME = "department";

    /**
     * 数据权限方法，将query绑定数据权限sql
     *
     * @param query    query
     * @param userInfo 用户信息
     */
    public static void dataScope(QueryWrapper query, SecurityUserInfo userInfo, String deptIdFrom) {
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
        if (DataScope.ALL.equals(dataScope)) {
            return;
        }
        if (DataScope.CUSTOMIZE.equals(dataScope)) {
            sql.append(STR."""
                          (\{deptIdFrom}dept_id in (select dept_id from sys_role_dept where role_id in (\{userInfo.getRoleCode()})))""");
        }
        if (DataScope.DEPARTMENT_BELOW.equals(dataScope)) {
            sql.append(STR."""
                         (\{deptIdFrom}dept_id in (select \{DEPT_TABLE_NAME}.dept_id from sys_dept_ancestors as \{DEPT_TABLE_NAME} where \{DEPT_TABLE_NAME}.ancestors = \{userInfo.getDeptId()}) or \{deptIdFrom}dept_id=\{userInfo.getDeptId()})
                          """);

        }
        if (DataScope.DEPARTMENT.equals(dataScope)) {
            sql.append(STR."""
                         (\{deptIdFrom}dept_id=\{userInfo.getDeptId().toString()})
                        """);
        }
        if (DataScope.ONESELF.equals(dataScope)) {
            sql.append(STR."""
                         (\{deptIdFrom}create_user = \{userInfo.getUserId().toString()})
                        """);
        }

        if (sql.isEmpty()) {
            return;
        }
        query.and(STR."(\{sql})");
        log.debug("[DataScopeUtils]->dataScope sql:{}",query.toSQL());
    }

    public static void dataScope(QueryWrapper query, SecurityUserInfo userInfo) {
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
