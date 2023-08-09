package cn.poem.solon.system.service;

import cn.poem.solon.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.vo.PoemRoleVo;
import com.mybatisflex.core.service.IService;

/**
 * 角色service
 *
 * @author hans
 */
public interface IPoemRoleService extends IService<PoemRole> {


    /**
     * 修改角色以及角色菜单关系
     * @param roleId 角色id
     * @return 角色视图对象，包含菜单信息
     */
    PoemRoleVo info(Long roleId);

    /**
     * 保存角色以及角色菜单关系
     * @param poemRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    boolean save(PoemRoleFromDTO poemRoleFromDTO);

    /**
     * 修改角色以及角色菜单关系
     * @param poemRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    boolean update(PoemRoleFromDTO poemRoleFromDTO);
}
