package cn.poem.solon.admin.utils;

import cn.poem.solon.admin.PoemServiceImpl;
import cn.poem.solon.admin.common.constant.BusTopicConstant;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.common.enums.DataScope;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.noear.dami.Dami;
import org.noear.solon.Utils;

import java.text.MessageFormat;
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
     * @param query query
     * @param userInfo 用户信息
     */
    public static void dataScope(QueryWrapper query, SecurityUserInfo userInfo,String deptIdFrom){
        if(userInfo.getAdmin()){
            return;
        }
        Set<DataScope> dataScopes = Optional.ofNullable(userInfo.getDataScope()).orElseGet(HashSet::new);
        if(Utils.isNotEmpty(deptIdFrom)){
            deptIdFrom=STR."""
                     "\{deptIdFrom}".""";
        }else{
            deptIdFrom="";
        }
        StringBuilder sql=new StringBuilder();
        int count=dataScopes.size();
        for (DataScope dataScope : dataScopes) {
            if (DataScope.ALL.equals(dataScope)) {
                break;
            }
            if (DataScope.CUSTOMIZE.equals(dataScope)) {
                sql.append(STR."""
                          (\{deptIdFrom}dept_id in (select dept_id from sys_role_dept where role_id in (\{StringUtil.join(",",userInfo.getRoleIds().stream().map(Object::toString).toList())})))""");
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

            count--;
            if(count>0){
                sql.append(" or ");
            }
            if(sql.isEmpty()){
                continue;
            }
        }
        query.and("("+sql+")");
        log.debug("sql=================="+query.toSQL());
    }

    public static void dataScope(QueryWrapper query, SecurityUserInfo userInfo){
        dataScope(query,userInfo,null);
    }
    public static QueryWrapper dataScope(QueryWrapper query){
        dataScope(query,getUserInfo());
        return query;
    }

    public static QueryWrapper dataScope(QueryWrapper query,String deptIdFrom){
        dataScope(query,getUserInfo(),deptIdFrom);
        return query;
    }


    private static SecurityUserInfo getUserInfo(){
        return Dami.<String, SecurityUserInfo>bus().sendAndRequest(BusTopicConstant.USER_INFO_TOPIC,null);
    }

    private DataScopeUtils(){}

}
