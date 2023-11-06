package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.system.domain.entity.PoemUserRole;
import cn.poem.solon.admin.event.system.entity.table.PoemUserRoleTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface PoemUserRoleMapper extends BaseMapper<PoemUserRole> {
    PoemUserRoleTableDef POEM_USER_ROLE = PoemUserRoleTableDef.POEM_USER_ROLE;

    /**
     * 根绝用户ID删除数据
     *
     * @param userId 用户id
     * @return 受影响的行数
     */
    default int deleteByUserId(Long userId) {
        return this.deleteByQuery(QueryWrapper.create().where(
                        POEM_USER_ROLE.USER_ID.eq(userId)
                )
        );
    }

    /**
     * 根据userId查询
     *
     * @param userId 用户id
     * @return 用户角色关系对象
     */
    default List<PoemUserRole> selectByUserId(Long userId) {
        return this.selectListByQuery(QueryWrapper.create().where(
                        POEM_USER_ROLE.USER_ID.eq(userId)
                )
        );
    }

}
