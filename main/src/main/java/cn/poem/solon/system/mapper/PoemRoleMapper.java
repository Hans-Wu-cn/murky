package cn.poem.solon.system.mapper;

import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.entity.table.PoemRoleTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

public interface PoemRoleMapper extends BaseMapper<PoemRole> {

    /**
     * 根据角色码查询数量
     * @return
     */
    default Long selectCountByRoleCode(String code){
        return this.selectCountByQuery(QueryWrapper.create()
                .from(PoemRoleTableDef.POEM_ROLE)
                .where(
                        PoemRoleTableDef.POEM_ROLE.ROLE_CODE.eq(code)
                )
        );
    }
}
