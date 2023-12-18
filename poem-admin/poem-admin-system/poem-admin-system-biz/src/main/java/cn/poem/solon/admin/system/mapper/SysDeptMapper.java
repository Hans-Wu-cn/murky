package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.system.domain.entity.SysDept;
import cn.poem.solon.admin.system.domain.entity.table.SysDeptTableDef;
import cn.poem.solon.admin.utils.DataScopeUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDept> {

    default List<SysDept> getSelectByCreate(SecurityUserInfo userInfo){
        SysDeptTableDef SYS_DEPT = SysDeptTableDef.SYS_DEPT;
        QueryWrapper queryWrapper = QueryWrapper.create().select().from(SYS_DEPT)
                .orderBy(SYS_DEPT.SORT.asc(), SYS_DEPT.DEPT_NAME.asc());
        DataScopeUtils.dataScope(queryWrapper, userInfo);
        return this.selectListByQuery(queryWrapper);
    }

}
