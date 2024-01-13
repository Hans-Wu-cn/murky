package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.system.biz.domain.entity.SysRoleMenu;
import cn.murky.admin.system.biz.domain.entity.table.SysRoleMenuTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色id
     * @return 受影响行数
     */
    default int deleteByRoleId(Long roleId) {
        SysRoleMenuTableDef SYS_ROLE_MENU = SysRoleMenuTableDef.SYS_ROLE_MENU;
        return this.deleteByQuery(QueryWrapper.create().where(
                SYS_ROLE_MENU.FK_ROLE_ID.eq(roleId)
                )
        );
    }

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return 角色菜单关系对象
     */
    default List<SysRoleMenu> selectByRoleId(Long roleId) {
        SysRoleMenuTableDef SYS_ROLE_MENU = SysRoleMenuTableDef.SYS_ROLE_MENU;
        return this.selectListByQuery(QueryWrapper.create().where(
                SYS_ROLE_MENU.FK_ROLE_ID.eq(roleId)
                )
        );
    }

}
