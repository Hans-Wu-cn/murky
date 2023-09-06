package cn.poem.solon.expand;

import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.core.extension.BaseEntity;
import cn.poem.solon.system.enums.DataScope;
import cn.poem.solon.utils.SecurityUtils;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.core.dialect.KeywordWrap;
import com.mybatisflex.core.dialect.LimitOffsetProcessor;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.*;
import com.mybatisflex.core.table.TableInfo;
import com.mybatisflex.core.table.TableInfoFactory;
import com.mybatisflex.core.util.StringUtil;
import org.mapstruct.ap.internal.util.Strings;
import org.noear.solon.validation.util.StringUtils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mybaits-flex字段填充器
 *
 * @author hans
 */
public class PoemFlexImpl extends CommonsDialectImpl implements InsertListener, UpdateListener {

    static Map<String, Boolean> HAS_USER_ID_COLUMNS = new ConcurrentHashMap<>();
    static Map<String, Boolean> HAS_DEPT_ID_COLUMNS = new ConcurrentHashMap<>();

    public PoemFlexImpl() {
        super(KeywordWrap.DOUBLE_QUOTATION, LimitOffsetProcessor.POSTGRESQL);
    }

    @Override
    public String forSelectByQuery(QueryWrapper queryWrapper) {
        QueryCondition condition = CPI.getWhereQueryCondition(queryWrapper);

        if (StpUtil.isLogin()) {
            SecurityUserInfo userInfo = SecurityUtils.getUserInfo();
            Set<DataScope> dataScope = userInfo.getDataScope();
            String rolids = Strings.join(userInfo.getRoleIds(), ",");
            for (DataScope scope : dataScope) {
                dataScopeFilter(queryWrapper, scope, condition, userInfo.getDeptId(), rolids);
            }
        }

//        }
        return super.buildSelectSql(queryWrapper);
    }

    /**
     * 新增sql 字段填充
     *
     * @param o sql对象
     */
    @Override
    public void onInsert(Object o) {
        if (o instanceof BaseEntity baseEntity) {
            baseEntity.setCreateTime(LocalDateTime.now());
            baseEntity.setCreateUser(SecurityUtils.getUserId());
        }
    }

    /**
     * 修改sql 字段填充
     *
     * @param o sql对象
     */
    @Override
    public void onUpdate(Object o) {
        if (o instanceof BaseEntity baseEntity) {
            baseEntity.setUpdateTime(LocalDateTime.now());
            baseEntity.setUpdateUser(SecurityUtils.getUserId());

        }
    }

    private void hasColums(TableInfo tableInfo) {
        String[] allColumns = tableInfo.getAllColumns();
        boolean has_dept = false;
        boolean has_user = false;
        for (String columns : allColumns) {
            if ("dept_id".equals(columns)) {
                has_dept = true;
            }
            if ("create_user".equals(columns)) {
                has_user = true;
            }
        }
        HAS_USER_ID_COLUMNS.put(tableInfo.getTableName(), has_user);
        HAS_DEPT_ID_COLUMNS.put(tableInfo.getTableName(), has_dept);
    }

    private Boolean hasUser(String nameWithSchema) {
        TableInfo tableInfo = TableInfoFactory.ofTableName(nameWithSchema);
        Boolean b = HAS_USER_ID_COLUMNS.get(tableInfo.getTableName());
        return Optional.ofNullable(b).orElseGet(() -> {
            hasColums(tableInfo);
            return HAS_USER_ID_COLUMNS.get(tableInfo.getTableName());
        });
    }

    private Boolean hasDept(String nameWithSchema) {
        TableInfo tableInfo = TableInfoFactory.ofTableName(nameWithSchema);
        Boolean b = HAS_USER_ID_COLUMNS.get(tableInfo.getTableName());
        return Optional.ofNullable(b).orElseGet(() -> {
            hasColums(tableInfo);
            return HAS_DEPT_ID_COLUMNS.get(tableInfo.getTableName());
        });
    }

    private void dataScopeFilter(QueryWrapper queryWrapper, DataScope dataScope, QueryCondition condition, Long deptId, String roleIds) {
        StringBuilder sqlString = new StringBuilder();
        String nameWithSchema = condition.getColumn().getTable().getNameWithSchema();
        if (DataScope.CUSTOMIZE.equals(dataScope) && hasDept(nameWithSchema)) {
            sqlString.append(MessageFormat.format(" OR {0}dept_id IN (select dept_id from poem_role_dept where role_id in ({1})) ", alias(condition), roleIds));
        }
        if (DataScope.DEPARTMENT_BELOW.equals(dataScope) && hasDept(nameWithSchema)) {
            sqlString.append(MessageFormat.format(" OR {0}dept_id IN (select dept_id from poem_dept_ancestors where ancestors = {1}) OR {0}dept_id = {1}", alias(condition), deptId));
        }
        if (DataScope.DEPARTMENT.equals(dataScope) && hasDept(nameWithSchema)) {
            sqlString.append(MessageFormat.format(" OR {0}dept_id = {1}", alias(condition), deptId));
        }
        if (DataScope.ONESELF.equals(dataScope) && hasUser(nameWithSchema)) {
            sqlString.append(MessageFormat.format(" OR {0}create_user = {1}", alias(condition), SecurityUtils.getUserId()));
        }

        if (!sqlString.isEmpty()) {
            String sql = MessageFormat.format("and ({0})", sqlString);
            queryWrapper.and(sql);
        }
    }

    private String alias(QueryCondition condition) {
        QueryColumn column = condition.getColumn();
        if (StringUtil.isNotBlank(column.getAlias())) {
            return column.getAlias() + ".";
        }
        return column.getName() + ".";
    }
}
