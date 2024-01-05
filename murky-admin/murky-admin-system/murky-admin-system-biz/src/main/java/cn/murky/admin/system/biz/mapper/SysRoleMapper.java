package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.system.biz.domain.entity.SysRole;
import cn.murky.admin.system.biz.domain.entity.table.SysRoleTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据角色码查询数量
     *
     * @return 返回数量
     */
    default SysRole selectByRoleNameAndRoleCode(String name, String code) {
        SysRoleTableDef SYS_ROLE = SysRoleTableDef.SYS_ROLE;
        return this.selectOneByQuery(QueryWrapper.create()
                .from(SYS_ROLE)
                .where(
                        SYS_ROLE.ROLE_CODE.eq(code)
                ).or(SYS_ROLE.ROLE_NAME.eq(name)).limit(1)
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
    default SysRole selectByNameOrCode(Long roleId, String name, String code) {
        SysRoleTableDef SYS_ROLE = SysRoleTableDef.SYS_ROLE;
        return this.selectOneByQuery(QueryWrapper.create().where(
                SYS_ROLE.ROLE_ID.ne(roleId, If::notNull)
        ).and(SYS_ROLE.ROLE_CODE.eq(code).or(SYS_ROLE.ROLE_NAME.eq(name))).limit(1));
    }
}
