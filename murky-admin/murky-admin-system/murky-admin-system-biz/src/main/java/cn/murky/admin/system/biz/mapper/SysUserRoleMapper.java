package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.system.biz.domain.entity.SysUserRole;
import cn.murky.admin.system.biz.domain.entity.table.SysUserRoleTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 根绝用户ID删除数据
     *
     * @param userId 用户id
     * @return 受影响的行数
     */
    default int deleteByUserId(Long userId) {
        SysUserRoleTableDef SYS_USER_ROLE = SysUserRoleTableDef.SYS_USER_ROLE;
        return this.deleteByQuery(QueryWrapper.create().where(
                        SYS_USER_ROLE.USER_ID.eq(userId)
                )
        );
    }

    /**
     * 根据userId查询
     *
     * @param userId 用户id
     * @return 用户角色关系对象
     */
    default List<SysUserRole> selectByUserId(Long userId) {
        SysUserRoleTableDef SYS_USER_ROLE = SysUserRoleTableDef.SYS_USER_ROLE;
        return this.selectListByQuery(QueryWrapper.create().where(
                        SYS_USER_ROLE.USER_ID.eq(userId)
                )
        );
    }

}
