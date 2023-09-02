package cn.poem.solon.system.mapper;

import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.entity.table.PoemDeptTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface PoemDeptMapper extends BaseMapper<PoemDept> {
    PoemDeptTableDef POEM_DEPT = PoemDeptTableDef.POEM_DEPT;

    default List<PoemDept> getSelectByCreate(Long userId){
        return this.selectListByQuery(QueryWrapper.create().select().from(POEM_DEPT)
                .where(POEM_DEPT.CREATE_USER.eq(userId)).limit(1)
        );
    }
}
