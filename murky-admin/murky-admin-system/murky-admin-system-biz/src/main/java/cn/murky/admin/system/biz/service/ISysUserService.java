package cn.murky.admin.system.biz.service;

import cn.murky.admin.flex.domin.SysUser;
import cn.murky.admin.system.biz.domain.dto.ResetPasswordDto;
import cn.murky.admin.system.biz.domain.dto.SysUserFromDTO;
import cn.murky.admin.system.biz.domain.dto.SysUserPageDTO;
import cn.murky.admin.system.biz.domain.vo.SysUserPageVo;
import cn.murky.admin.system.biz.domain.vo.SysUserVo;
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
     *
     * @param resetPasswordDto
     * @return 重置成功状态
     */
    boolean resetPassword(ResetPasswordDto resetPasswordDto);

    /**
     * 重写分页方法
     */
    Page<SysUserPageVo> page(SysUserPageDTO sysUserPageDTO);
}
