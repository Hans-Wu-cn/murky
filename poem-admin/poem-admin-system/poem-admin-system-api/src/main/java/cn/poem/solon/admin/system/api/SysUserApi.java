package cn.poem.solon.admin.system.api;


import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.domin.SysUser;
import cn.poem.solon.admin.system.api.domian.UserProfile;
import cn.poem.solon.admin.system.api.domian.dto.ProfileFromDTO;

/**
 * 用户Api
 */
public interface SysUserApi {
    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    SysUser getOneByAccount(String account);

    /**
     * 获取用户详情
     * @return
     */
    SecurityUserInfo userInfo();

    /**
     * 获取用户profile
     * @return
     */
    UserProfile getProfile(Long userId);

    /**
     * 获取用户profile
     *
     * @return
     */
    boolean setProfile(ProfileFromDTO profileFromDTO);

    /**
     * 设置用户语言偏好
     * @return
     */
    boolean setLanguage(String language);
}