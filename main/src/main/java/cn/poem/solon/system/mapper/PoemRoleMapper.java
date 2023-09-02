package cn.poem.solon.system.mapper;

import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.entity.table.PoemRoleTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface PoemRoleMapper extends BaseMapper<PoemRole> {
    PoemRoleTableDef POEM_ROLE = PoemRoleTableDef.POEM_ROLE;

    /**
     * 根据角色码查询数量
     *
     * @return 返回数量
     */
    default PoemRole selectByRoleNameAndRoleCode(String name, String code) {
        return this.selectOneByQuery(QueryWrapper.create()
                .from(POEM_ROLE)
                .where(
                        POEM_ROLE.ROLE_CODE.eq(code)
                ).or(POEM_ROLE.ROLE_NAME.eq(name)).limit(1)
        );
    }

    /**
     * 根据角色名称或角色码查询角色信息
     *
     * @param roleId 角色id
     * @param name   角色名称
     * @param code   角色码
     * @return 符合条件的角色集合
     */
    default PoemRole selectByNameOrCode(Long roleId, String name, String code) {
        return this.selectOneByQuery(QueryWrapper.create().where(
                POEM_ROLE.ROLE_ID.ne(roleId, If::notNull)
        ).and(POEM_ROLE.ROLE_CODE.eq(code).or(POEM_ROLE.ROLE_NAME.eq(name))).limit(1));
    }
}
