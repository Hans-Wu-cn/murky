package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.domin.PoemDeptAncestors;
import cn.poem.solon.admin.domin.table.PoemDeptAncestorsTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

/**
 * 部门关系mapper
 * @author hans
 */
public interface PoemDeptAncestorsMapper extends BaseMapper<PoemDeptAncestors> {
    PoemDeptAncestorsTableDef POEM_DEPT_ANCESTORS = PoemDeptAncestorsTableDef.POEM_DEPT_ANCESTORS;

    /**
     * 根据部门id查询
     * @param deptId 部门id
     * @return 相关数据
     */
    default List<PoemDeptAncestors> getListByDeptId(Long deptId){

        return this.selectListByQuery(QueryWrapper.create()
                .from(POEM_DEPT_ANCESTORS)
                .where(POEM_DEPT_ANCESTORS.DEPT_ID.eq(deptId)));
    }

    /**
     * 根据祖先查询
     * @param ancestors 祖先
     * @return 相关数据
     */
    default List<PoemDeptAncestors> getListByAncestors(Long ancestors){

        return this.selectListByQuery(QueryWrapper.create()
                .from(POEM_DEPT_ANCESTORS)
                .where(POEM_DEPT_ANCESTORS.ANCESTORS.eq(ancestors, If::notNull)));
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
     *
     * @param deptId 部门id
     */
    default int deleteByDeptId(Long deptId){

        return this.deleteByQuery(QueryWrapper.create()
                .from(POEM_DEPT_ANCESTORS)
                .where(POEM_DEPT_ANCESTORS.DEPT_ID.eq(deptId)));
    }
}
