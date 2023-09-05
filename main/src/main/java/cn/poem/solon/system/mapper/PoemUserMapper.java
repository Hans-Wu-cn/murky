package cn.poem.solon.system.mapper;

import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

public interface PoemUserMapper extends BaseMapper<PoemUser> {
    PoemUserTableDef POEM_USER = PoemUserTableDef.POEM_USER;

    /**
     * 根据部门id查询用户数量
     * @param deptId
     * @return
     */
    default Long getCountByDeptId(Long deptId){
        return selectCountByQuery(QueryWrapper.create()
                .from(POEM_USER)
                .where(POEM_USER.DEPT_ID.eq(deptId))
        );
    }
}
