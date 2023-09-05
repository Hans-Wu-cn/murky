package cn.poem.solon.system.mapper;

import cn.poem.solon.system.domain.entity.PoemDeptAncestors;
import cn.poem.solon.system.domain.entity.table.PoemDeptAncestorsTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

/**
 * 部门关系mapper
 * @author hans
 */
public interface PoemDeptAncestorsMapper extends BaseMapper<PoemDeptAncestors> {
    PoemDeptAncestorsTableDef POEM_DEPT_ANCESTORS = PoemDeptAncestorsTableDef.POEM_DEPT_ANCESTORS;

    /**
     * 根据deptId查询数量
     * @param deptId
     * @return 数量
     */
    default List<PoemDeptAncestors> getListByDeptId(Long deptId){

        return this.selectListByQuery(QueryWrapper.create()
                .from(POEM_DEPT_ANCESTORS)
                .where(POEM_DEPT_ANCESTORS.DEPT_ID.eq(deptId)));
    }

    /**
     * 根据Ancestors查询数量
     * @param deptId 部门id
     * @return 数量
     */
    default Long getCountByAncestors(Long deptId){

        return this.selectCountByQuery(QueryWrapper.create()
                .from(POEM_DEPT_ANCESTORS)
                .where(POEM_DEPT_ANCESTORS.ANCESTORS.eq(deptId)));
    }

    /**
     * 根绝部门id删除
     * @param deptId 部门id
     * @return 影响行
     */
    default Integer deleteByDeptId(Long deptId){

        return this.deleteByQuery(QueryWrapper.create()
                .from(POEM_DEPT_ANCESTORS)
                .where(POEM_DEPT_ANCESTORS.DEPT_ID.eq(deptId)));
    }
}
