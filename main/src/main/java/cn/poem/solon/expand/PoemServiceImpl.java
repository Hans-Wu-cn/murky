package cn.poem.solon.expand;

import cn.poem.solon.system.domain.entity.table.PoemDeptAncestorsTableDef;
import cn.poem.solon.system.domain.entity.table.PoemRoleDeptTableDef;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.system.enums.DataScope;
import cn.poem.solon.utils.SecurityUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.solon.service.impl.ServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class PoemServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IService<T> {
    PoemUserTableDef POEM_USER = PoemUserTableDef.POEM_USER;
    PoemRoleDeptTableDef POEM_ROLE_DEPT = PoemRoleDeptTableDef.POEM_ROLE_DEPT;
    PoemDeptAncestorsTableDef POEM_DEPT_ANCESTORS = PoemDeptAncestorsTableDef.POEM_DEPT_ANCESTORS;


    protected QueryWrapper dataScope(QueryWrapper query,SecurityUserInfo userInfo){
        Set<DataScope> dataScopes = Optional.ofNullable(userInfo.getDataScope()).orElseGet(HashSet::new);
        for (DataScope dataScope : dataScopes) {
            if (DataScope.CUSTOMIZE.equals(dataScope)) {
                query.or(POEM_USER.DEPT_ID.in(QueryWrapper.create().from(POEM_ROLE_DEPT).where(POEM_ROLE_DEPT.ROLE_ID.in(userInfo.getRoleIds()))));
            }
            if (DataScope.DEPARTMENT_BELOW.equals(dataScope)) {
                QueryWrapper chrendQuery = QueryWrapper.create().from(POEM_DEPT_ANCESTORS).where(POEM_DEPT_ANCESTORS.ANCESTORS.eq(userInfo.getDeptId()));
                query.or(POEM_USER.DEPT_ID.in(chrendQuery).or(POEM_USER.DEPT_ID.eq(userInfo.getDeptId())));
            }
            if (DataScope.DEPARTMENT.equals(dataScope)) {
                query.or(POEM_ROLE_DEPT.DEPT_ID.eq(userInfo.getDeptId()));
            }
            if (DataScope.ONESELF.equals(dataScope)) {
                query.or(POEM_USER.CREATE_USER.eq(userInfo.getUserId()));
            }
        }
        return query;
    }
}
