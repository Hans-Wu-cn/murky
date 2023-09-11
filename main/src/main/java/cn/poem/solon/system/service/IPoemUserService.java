package cn.poem.solon.system.service;

import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.system.domain.vo.PoemUserVo;
import com.mybatisflex.core.service.IService;

/**
 * 用户 Service
 *
 * @author hans
 */
public interface IPoemUserService extends IService<PoemUser> {

    /**
     * 根据用户id查询用户详细信息，包含角色信息
     * @param userId 用户id
     * @return 用户视图对象
     */
    PoemUserVo info(Long userId);

    /**
     * 添加用户并绑定对应的角色关系
     * @param poemUserFromDTO
     * @return 保存成功状态
     */
    boolean save(PoemUserFromDTO poemUserFromDTO);

    /**
     * 修改用户并绑定对应的角色关系
     * @param poemUserFromDTO
     * @return 保存成功状态
     */
    boolean update(PoemUserFromDTO poemUserFromDTO);

    /**
     * 重置用户密码
     * @param userId 用户id
     * @param password 密码
     * @return 重置成功状态
     */
    boolean resetPassword(Long userId,String password);
}
