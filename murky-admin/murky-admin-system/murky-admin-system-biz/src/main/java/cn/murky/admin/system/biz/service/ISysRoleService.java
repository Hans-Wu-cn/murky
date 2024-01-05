package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.biz.domain.dto.SysRoleFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysRoleVo;
import cn.murky.admin.system.biz.domain.entity.SysRole;
import com.mybatisflex.core.service.IService;

/**
 * 角色service
 *
 * @author hans
 */
public interface ISysRoleService extends IService<SysRole> {


    /**
     * 查询角色以及角色菜单关系
     * @param roleId 角色id
     * @return 角色视图对象，包含菜单信息
     */
    SysRoleVo info(Long roleId);

    /**
     * 保存角色以及角色菜单关系
     * @param sysRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    boolean save(SysRoleFromDTO sysRoleFromDTO);

    /**
     * 修改角色以及角色菜单关系
     * @param sysRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    boolean update(SysRoleFromDTO sysRoleFromDTO);
}
