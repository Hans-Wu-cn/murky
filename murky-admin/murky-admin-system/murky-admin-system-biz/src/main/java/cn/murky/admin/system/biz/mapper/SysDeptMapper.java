package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.common.entity.SecurityUserInfo;
import cn.murky.admin.system.biz.domain.entity.SysDept;
import cn.murky.admin.flex.utils.DataScopeUtils;
import cn.murky.admin.system.biz.domain.entity.table.SysDeptTableDef;
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
