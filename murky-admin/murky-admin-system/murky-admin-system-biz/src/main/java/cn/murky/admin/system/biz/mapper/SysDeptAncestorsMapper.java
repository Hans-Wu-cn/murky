package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.system.biz.domain.entity.SysDeptAncestors;
import cn.murky.admin.system.biz.domain.entity.table.SysDeptAncestorsTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

/**
 * 部门关系mapper
 * @author hans
 */
public interface SysDeptAncestorsMapper extends BaseMapper<SysDeptAncestors> {
    /**
     * 根据部门id查询
     * @param deptId 部门id
     * @return 相关数据
     */
    default List<SysDeptAncestors> getListByDeptId(Long deptId){

        SysDeptAncestorsTableDef SYS_DEPT_ANCESTORS = SysDeptAncestorsTableDef.SYS_DEPT_ANCESTORS;
        return this.selectListByQuery(QueryWrapper.create()
                .from(SYS_DEPT_ANCESTORS)
                .where(SYS_DEPT_ANCESTORS.FK_DEPT_ID.eq(deptId)));
    }

    /**
     * 根据祖先查询
     * @param ancestors 祖先
     * @return 相关数据
     */
    default List<SysDeptAncestors> getListByAncestors(Long ancestors){

        SysDeptAncestorsTableDef SYS_DEPT_ANCESTORS = SysDeptAncestorsTableDef.SYS_DEPT_ANCESTORS;
        return this.selectListByQuery(QueryWrapper.create()
                .from(SYS_DEPT_ANCESTORS)
                .where(SYS_DEPT_ANCESTORS.ANCESTORS.eq(ancestors, If::notNull)));
    }

    /**
     * 根据Ancestors查询数量
     * @param deptId 部门id
     * @return 数量
     */
    default Long getCountByAncestors(Long deptId){

        SysDeptAncestorsTableDef SYS_DEPT_ANCESTORS = SysDeptAncestorsTableDef.SYS_DEPT_ANCESTORS;
        return this.selectCountByQuery(QueryWrapper.create()
                .from(SYS_DEPT_ANCESTORS)
                .where(SYS_DEPT_ANCESTORS.ANCESTORS.eq(deptId)));
    }

    /**
     * 根据部门id删除
     *
     * @param deptId 部门id
     */
    default int deleteByDeptId(Long deptId){

        SysDeptAncestorsTableDef SYS_DEPT_ANCESTORS = SysDeptAncestorsTableDef.SYS_DEPT_ANCESTORS;
        return this.deleteByQuery(QueryWrapper.create()
                .from(SYS_DEPT_ANCESTORS)
                .where(SYS_DEPT_ANCESTORS.FK_DEPT_ID.eq(deptId)));
    }
}
