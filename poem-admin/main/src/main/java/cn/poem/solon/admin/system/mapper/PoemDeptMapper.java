package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.entity.SecurityUserInfo;
import cn.poem.solon.admin.system.domain.entity.PoemDept;
import cn.poem.solon.admin.system.domain.entity.table.PoemDeptTableDef;
import cn.poem.solon.admin.utils.DataScopeUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface PoemDeptMapper extends BaseMapper<PoemDept> {
    PoemDeptTableDef POEM_DEPT = PoemDeptTableDef.POEM_DEPT;

    default List<PoemDept> getSelectByCreate(SecurityUserInfo userInfo){
        QueryWrapper queryWrapper = QueryWrapper.create().select().from(POEM_DEPT)
                .orderBy(POEM_DEPT.SORT.asc(), POEM_DEPT.DEPT_NAME.asc());
        queryWrapper = DataScopeUtils.dataScope(queryWrapper, userInfo);
        return this.selectListByQuery(queryWrapper);
    }
}
