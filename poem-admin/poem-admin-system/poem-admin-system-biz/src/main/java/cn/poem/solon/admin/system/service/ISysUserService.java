package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.domin.SysUser;
import cn.poem.solon.admin.system.domain.dto.SysUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.SysUserPageDTO;
import cn.poem.solon.admin.system.domain.vo.SysUserPageVo;
import cn.poem.solon.admin.system.domain.vo.SysUserVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

/**
 * 用户 Service
 *
 * @author hans
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户id查询用户详细信息，包含角色信息
     * @param userId 用户id
     * @return 用户视图对象
     */
    SysUserVo info(Long userId);

    /**
     * 添加用户并绑定对应的角色关系
     * @return 保存成功状态
     */
    boolean save(SysUserFromDTO sysUserFromDTO);

    /**
     * 修改用户并绑定对应的角色关系
     * @return 保存成功状态
     */
    boolean update(SysUserFromDTO sysUserFromDTO);

    /**
     * 重置用户密码
     * @param userId 用户id
     * @param password 密码
     * @return 重置成功状态
     */
    boolean resetPassword(Long userId,String password);

    /**
     * 重写分页方法
     */
    Page<SysUserPageVo> page(SysUserPageDTO sysUserPageDTO);
}
